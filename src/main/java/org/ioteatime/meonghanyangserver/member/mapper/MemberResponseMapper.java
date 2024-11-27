package org.ioteatime.meonghanyangserver.member.mapper;

import org.ioteatime.meonghanyangserver.group.domain.GroupEntity;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.response.GroupDetailResponse;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberDetailResponse;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberWithGroupDetailResponse;

public class MemberResponseMapper {
    public static MemberWithGroupDetailResponse from(MemberEntity member, GroupEntity group) {
        MemberDetailResponse memberDetail = MemberDetailResponse.from(member);
        if (group != null) {
            GroupDetailResponse groupDetail = GroupDetailResponse.from(group);
            return MemberWithGroupDetailResponse.builder()
                    .member(memberDetail)
                    .group(groupDetail)
                    .build();
        }
        return MemberWithGroupDetailResponse.builder().member(memberDetail).build();
    }
}
