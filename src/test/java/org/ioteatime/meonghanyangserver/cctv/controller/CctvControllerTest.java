package org.ioteatime.meonghanyangserver.cctv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.clients.kvs.KvsClient;
import org.ioteatime.meonghanyangserver.common.type.CommonErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.config.ControllerTestConfig;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.group.service.GroupService;
import org.ioteatime.meonghanyangserver.groupmember.doamin.GroupMemberEntity;
import org.ioteatime.meonghanyangserver.groupmember.doamin.enums.GroupMemberRole;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@SpringBootTest
class CctvControllerTest extends ControllerTestConfig {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private KvsClient kvsClient;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CctvRepository cctvRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupMemberRepository groupMemberRepository;

    @Autowired private GroupService groupService;

    private String accessToken;
    private MemberEntity member;

    @BeforeEach
    public void beforeEach() {
        member =
                MemberEntity.builder()
                        .email("test@gmail.com")
                        .nickname("test")
                        .password("testpassword")
                        .build();
        memberRepository.save(member);
        accessToken =
                jwtUtils.includeBearer(
                        jwtUtils.generateAccessToken(member.getNickname(), member.getId()));
    }

    @Test
    void CCTV를_삭제합니다() throws Exception {
        GroupEntity group =
                GroupEntity.builder().groupName("testgroup").fcmTopic("testTopic").build();
        groupRepository.save(group);

        GroupMemberEntity groupMember =
                GroupMemberEntity.builder()
                        .group(group)
                        .member(member)
                        .thingId("master-thing-id")
                        .role(GroupMemberRole.ROLE_MASTER)
                        .build();
        groupMemberRepository.save(groupMember);

        String kvsChannelName = "test-delete-channel";

        CctvEntity cctv =
                CctvEntity.builder()
                        .group(group)
                        .cctvNickname("test")
                        .kvsChannelName(kvsChannelName) // KVS에 채널이 존재해야 함
                        .thingId("test-thing-id")
                        .build();

        cctvRepository.save(cctv);

        kvsClient.createSignalingChannel(kvsChannelName); // KVS 채널 생성

        mockMvc.perform(
                        delete("/api/cctv/{cctvId}/out", cctv.getId())
                                .header(HttpHeaders.AUTHORIZATION, accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("OK"))
                .andExpect(jsonPath("$.result.description").value("CCTV 삭제(퇴출)에 성공하였습니다."))
                .andDo(print());

        Assertions.assertThat(cctvRepository.existsByKvsChannelName(kvsChannelName))
                .isEqualTo(false);
        Assertions.assertThat(cctvRepository.findById(cctv.getId())).isEmpty();
    }

    @Test
    void 글로벌_예외_처리를_테스트합니다() throws Exception {
        // 일치하는 요청 경로가 없는 경우
        mockMvc.perform(get("/open-api/cctv/hello-world"))
                .andExpect(status().isInternalServerError())
                .andExpect(
                        jsonPath("$.result.description")
                                .value(CommonErrorType.ILLEGAL_ARGUMENT.getDescription()));
    }
}
