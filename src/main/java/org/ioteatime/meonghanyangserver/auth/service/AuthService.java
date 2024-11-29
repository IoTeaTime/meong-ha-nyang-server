package org.ioteatime.meonghanyangserver.auth.service;

import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import org.ioteatime.meonghanyangserver.auth.dto.db.LoginWithMemberInfo;
import org.ioteatime.meonghanyangserver.auth.dto.reponse.LoginResponse;
import org.ioteatime.meonghanyangserver.auth.dto.request.IssuePasswordRequest;
import org.ioteatime.meonghanyangserver.auth.dto.request.LoginRequest;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthEntityMapper;
import org.ioteatime.meonghanyangserver.auth.mapper.AuthResponseMapper;
import org.ioteatime.meonghanyangserver.clients.ses.SesClient;
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
    private final JwtUtils jwtUtils;
    private final SesClient sesClient;
    private final MemberRepository memberRepository;
    private final GroupMemberRepository deviceRepository;
    private final EmailCodeRepository emailCodeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    public LoginResponse login(LoginRequest loginRequest) {
        LoginWithMemberInfo memberInfo =
                memberRepository
                        .findGroupMemberInfoByEmail(loginRequest.email())
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));

        boolean passwordMatch =
                bCryptPasswordEncoder.matches(loginRequest.password(), memberInfo.password());
        if (!passwordMatch) {
            throw new BadRequestException(AuthErrorType.PASSWORD_NOT_MATCH);
        }

        String accessToken =
                jwtUtils.generateAccessToken(memberInfo.nickname(), memberInfo.memberId());
        String refreshToken =
                jwtUtils.generateRefreshToken(memberInfo.nickname(), memberInfo.memberId());

        if (accessToken.isEmpty() || refreshToken.isEmpty()) {
            throw new NotFoundException(AuthErrorType.TOKEN_NOT_FOUND);
        }
        RefreshToken refreshTokenEntity =
                RefreshToken.builder()
                        .memberId(memberInfo.memberId())
                        .refreshToken(refreshToken)
                        .build();

        refreshTokenRepository.save(refreshTokenEntity);
        accessToken = jwtUtils.includeBearer(accessToken);
        refreshToken = jwtUtils.includeBearer(refreshToken);

        return AuthResponseMapper.from(memberInfo, accessToken, refreshToken);
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
        sesClient.sendEmail(email, mailSubject, mailContent);
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

    @Transactional
    public void issuePassword(IssuePasswordRequest issuePasswordRequest) {

        MemberEntity memberEntity =
                memberRepository
                        .findByEmail(issuePasswordRequest.email())
                        .orElseThrow(() -> new NotFoundException(AuthErrorType.NOT_FOUND));
        // 임시비밀번호 발급
        String password = getCode();

        String encodedPassword = bCryptPasswordEncoder.encode(password);

        memberEntity.updatePassword(encodedPassword);
        String mailSubject = "[\uD83D\uDC36 멍하냥] 임시 비밀번호입니다.";
        String mailContent =
                """
                <b>임시 비밀번호를 발급받았습니다.</b>
                <p>%s</p>
                """
                        .formatted(password);
        sesClient.sendEmail(issuePasswordRequest.email(), mailSubject, mailContent);
    }
}
