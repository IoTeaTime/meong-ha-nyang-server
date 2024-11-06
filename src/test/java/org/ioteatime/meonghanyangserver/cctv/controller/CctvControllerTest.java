package org.ioteatime.meonghanyangserver.cctv.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.ioteatime.meonghanyangserver.cctv.repository.CctvRepository;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.config.ControllerTestConfig;
import org.ioteatime.meonghanyangserver.group.repository.GroupRepository;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CctvControllerTest extends ControllerTestConfig {
    @Autowired private JwtUtils jwtUtils;
    //    @Autowired private UserRepository userRepository;
    @Autowired private CctvRepository cctvRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupMemberRepository deviceRepository;

    private String accessToken;

    //    private UserEntity user;

    @BeforeEach
    public void beforeEach() {
        //        user =
        //                UserEntity.builder()
        //                        .email("test@gmail.com")
        //                        .nickname("test")
        //                        .password("testpassword")
        //                        .build();
        //        userRepository.save(user);
        //        accessToken = jwtUtils.includeBearer(jwtUtils.generateAccessToken(user));
    }

    @Test
    void CCTV를_삭제합니다() throws Exception {
        //        GroupEntity group = GroupEntity.builder().groupName("testgroup").build();
        //        groupRepository.save(group);
        //
        //        GroupMemberEntity device2 =
        //                GroupMemberEntity.builder()
        //                        .user(user)
        //                        .group(group)
        //                        .deviceUuid("test2")
        //                        .role(DeviceRole.ROLE_CCTV)
        //                        .build();
        //        device2 = deviceRepository.save(device2);
        //
        //        String kvsChannelName = "delete-test-channel"; // KVS에 채널이 존재해야 함
        //        CctvEntity cctv =
        //                CctvEntity.builder()
        //                        .cctvNickname("delcctv")
        //                        .device(device2)
        //                        .kvsChannelName(kvsChannelName)
        //                        .build();
        //        cctvRepository.save(cctv);
        //
        //        mockMvc.perform(
        //                        delete("/api/cctv/{cctvId}", cctv.getId())
        //                                .header(HttpHeaders.AUTHORIZATION, accessToken)
        //                                .contentType(MediaType.APPLICATION_JSON))
        //                .andExpect(status().isOk())
        //                .andExpect(jsonPath("$.result.message").value("OK"))
        //                .andExpect(jsonPath("$.result.description").value("CCTV 삭제(퇴출)에
        // 성공하였습니다."))
        //                .andDo(print());
        //
        //        Assertions.assertThat(cctvRepository.existsByKvsChannelName(kvsChannelName))
        //                .isEqualTo(false);
        //        Assertions.assertThat(deviceRepository.findByDeviceId(device2.getId())).isEmpty();
    }
}
