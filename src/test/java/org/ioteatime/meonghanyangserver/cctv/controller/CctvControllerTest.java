package org.ioteatime.meonghanyangserver.cctv.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.http.HttpHeaders;
import org.assertj.core.api.Assertions;
import org.ioteatime.meonghanyangserver.cctv.domain.CctvEntity;
import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.config.ControllerTestConfig;
import org.ioteatime.meonghanyangserver.device.doamin.DeviceEntity;
import org.ioteatime.meonghanyangserver.device.doamin.enums.DeviceRole;
import org.ioteatime.meonghanyangserver.device.repository.DeviceRepository;
import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

@SpringBootTest
class CctvControllerTest extends ControllerTestConfig {
    @Autowired private JwtUtils jwtUtils;
    @Autowired private MemberRepository memberRepository;
    @Autowired private CctvRepository cctvRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private DeviceRepository deviceRepository;

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
        accessToken = jwtUtils.includeBearer(jwtUtils.generateAccessToken(member));
    }

    @Test
    void CCTV를_삭제합니다() throws Exception {
        GroupEntity group = GroupEntity.builder().groupName("testgroup").build();
        groupRepository.save(group);

        DeviceEntity device2 =
                DeviceEntity.builder()
                        .member(member)
                        .group(group)
                        .deviceUuid("test2")
                        .role(DeviceRole.ROLE_CCTV)
                        .build();
        device2 = deviceRepository.save(device2);

        String kvsChannelName = "delete-test-channel"; // KVS에 채널이 존재해야 함
        CctvEntity cctv =
                CctvEntity.builder()
                        .cctvNickname("delcctv")
                        .device(device2)
                        .kvsChannelName(kvsChannelName)
                        .build();
        cctvRepository.save(cctv);

        mockMvc.perform(
                        delete("/api/cctv/{cctvId}", cctv.getId())
                                .header(HttpHeaders.AUTHORIZATION, accessToken)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("OK"))
                .andExpect(jsonPath("$.result.description").value("CCTV 삭제(퇴출)에 성공하였습니다."))
                .andDo(print());

        Assertions.assertThat(cctvRepository.existsByKvsChannelName(kvsChannelName))
                .isEqualTo(false);
        Assertions.assertThat(deviceRepository.findByDeviceId(device2.getId())).isEmpty();
    }
}
