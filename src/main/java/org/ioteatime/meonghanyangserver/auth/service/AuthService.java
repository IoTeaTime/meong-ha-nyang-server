package org.ioteatime.meonghanyangserver.auth.service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthEntityMapper;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.clients.google.GoogleMailClient;
import org.ioteatime.meonghanyangserver.common.exception.BadRequestException;
import org.ioteatime.meonghanyangserver.common.exception.NotFoundException;
import org.ioteatime.meonghanyangserver.common.exception.UnauthorizedException;
import org.ioteatime.meonghanyangserver.common.type.AuthErrorType;
import org.ioteatime.meonghanyangserver.common.utils.JwtUtils;
import org.ioteatime.meonghanyangserver.groupmember.repository.GroupMemberRepository;
import org.ioteatime.meonghanyangserver.member.domain.MemberEntity;
import org.ioteatime.meonghanyangserver.member.dto.request.JoinRequest;
import org.ioteatime.meonghanyangserver.member.dto.response.MemberSimpleResponse;
import org.ioteatime.meonghanyangserver.member.repository.MemberRepository;
import org.ioteatime.meonghanyangserver.redis.EmailCode;
import org.ioteatime.meonghanyangserver.redis.EmailCodeRepository;
import org.ioteatime.meonghanyangserver.redis.RefreshToken;
import org.ioteatime.meonghanyangserver.redis.RefreshTokenRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final GoogleMailClient googleMailClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailCodeRepository emailCodeRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final GroupMemberRepository deviceRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        MemberEntity memberEntity =
                memberRepository
                        .findByEmail(loginRequest.email())
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        boolean passwordMatch =
                bCryptPasswordEncoder.matches(loginRequest.password(), memberEntity.getPassword());
        if (!passwordMatch) {
            throw new BadRequestException(AuthErrorType.PASSWORD_NOT_MATCH);
        }

        String accessToken = jwtUtils.generateAccessToken(memberEntity);
        String refreshToken = jwtUtils.generateRefreshToken(memberEntity);

        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw new NotFoundException(AuthErrorType.TOKEN_NOT_FOUND);
        }
        RefreshToken refreshTokenEntity = RefreshToken.builder().refreshToken(refreshToken).build();

        refreshTokenRepository.save(refreshTokenEntity);
        accessToken = jwtUtils.includeBearer(accessToken);
        refreshToken = jwtUtils.includeBearer(refreshToken);

        return AuthResponseMapper.from(memberEntity.getId(), accessToken, refreshToken);
    }

    public MemberSimpleResponse joinProcess(JoinRequest userDto) {
        String encodedPassword = bCryptPasswordEncoder.encode(userDto.getPassword());
        verifyEmail(userDto.getEmail());
        MemberEntity member = memberRepository.save(AuthEntityMapper.of(userDto, encodedPassword));

        return AuthResponseMapper.from(member.getId(), member.getEmail());
    }

    public void send(String email) {
        String code = getCode();
        emailCodeRepository.save(EmailCode.builder().email(email).code(code).build());
        String mailSubject = "[\uD83D\uDC36 멍하냥] 이메일 인증 코드입니다.";
        String mailContent =
                """
                <h3>환영해요!</h3>
                <b>인증코드를 입력하세요</b>
                <p>%s</p>
                """
                        .formatted(code);
        googleMailClient.sendMail(email, mailSubject, mailContent);
    }

    private static String getCode() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        List<String> authStr = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 3; i++) {
            authStr.add(String.valueOf(random.nextInt(10)));
        }
        for (int i = 0; i < 3; i++) {
            authStr.add(String.valueOf((char) (random.nextInt(26) + 65)));
        }

        Collections.shuffle(authStr);
        return String.join("", authStr);
    }

    public void verifyEmail(String email) {
        if (memberRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException(AuthErrorType.EMAIL_DUPLICATED);
        }
    }

    public void verifyEmailCode(String email, String code) {
        EmailCode emailCode =
                emailCodeRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));
        if (!code.equals(emailCode.getCode())) {
            throw new UnauthorizedException(AuthErrorType.CODE_NOT_EQUALS);
        }
    }
}
