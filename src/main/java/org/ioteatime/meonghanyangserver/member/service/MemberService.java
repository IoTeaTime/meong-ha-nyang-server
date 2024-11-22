package org.ioteatime.meonghanyangserver.member.service;

import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.RefreshResponse;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.request.ChangePasswordRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberDetailResponse;
import org.ioteatime.meonghanyangserver.member.mapper.MemberResponseMapper;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.ioteatime.meonghanyangserver.redis.AccessToken;
import org.ioteatime.meonghanyangserver.redis.AccessTokenRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final JwtUtils jwtUtils;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AccessTokenRepository accessTokenRepository;

    public MemberDetailResponse getMemberDetail(Long memberId) {
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        return MemberResponseMapper.from(memberEntity);
    }

    public void deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public void changeMemberPassword(Long memberId, ChangePasswordRequest request) {
        String currentPassword = request.currentPassword();
        String newPassword = request.newPassword();

        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(currentPassword, memberEntity.getPassword())) {
            throw new BadRequestException(AuthErrorType.PASSWORD_NOT_MATCH);
        }

        // Dirty-Checking Password Change
        memberEntity.setPassword(bCryptPasswordEncoder.encode(newPassword));
    }

    public RefreshResponse reissueAccessToken(String authorizationHeader) {
        String refreshToken = jwtUtils.extractTokenFromHeader(authorizationHeader);

        Long memberId = jwtUtils.getIdFromToken(refreshToken);
        MemberEntity memberEntity =
                memberRepository
                        .findById(memberId)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        if (!jwtUtils.validateToken(refreshToken, memberEntity)) {
            throw new NotFoundException(AuthErrorType.REFRESH_TOKEN_INVALID);
        }

        RefreshToken storedToken =
                refreshTokenRepository
                        .findByRefreshToken(refreshToken)
                        .orElseThrow(
                                () -> new NotFoundException(AuthErrorType.REFRESH_TOKEN_NOT_FOUND));

        if (!storedToken.getRefreshToken().equals(refreshToken)) {
            throw new BadRequestException(AuthErrorType.TOKEN_NOT_EQUALS);
        }

        String newAccessToken = jwtUtils.generateAccessToken(memberEntity);
        newAccessToken = jwtUtils.includeBearer(newAccessToken);

        return AuthResponseMapper.from(newAccessToken);
    }

    @Transactional
    public void logout(Long memberId,String accessToken) {
        refreshTokenRepository.deleteByMemberId(memberId);
        accessToken = accessToken.substring(7);
        Date data = jwtUtils.getExpirationDateFromToken(accessToken);
        //access token의 남은 시간
        long ttl = (data.getTime() - System.currentTimeMillis()) / 1000;

        AccessToken accessTokenEntity = AccessToken.builder()
                .accessToken(accessToken)
                .ttl(ttl).build();
        //access token 블랙리스트
        accessTokenRepository.save(accessTokenEntity);

    }
}
