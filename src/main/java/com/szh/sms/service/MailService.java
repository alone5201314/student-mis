package com.szh.sms.service;

import com.szh.sms.dto.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class MailService {

    private static final Logger logger = LoggerFactory.getLogger(MailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendScoreEmail(String toEmail, String studentName, List<Course> scores) throws MessagingException {
        logger.info("开始发送邮件到: {}, 学生姓名: {}", toEmail, studentName);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("【瀚海大学】" + studentName + "的成绩单");

            String htmlContent = buildScoreHtml(studentName, scores);
            helper.setText(htmlContent, true);

            mailSender.send(message);
            logger.info("邮件发送成功到: {}", toEmail);
        } catch (Exception e) {
            logger.error("邮件发送失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    private String buildScoreHtml(String studentName, List<Course> scores) {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body>");
        sb.append("<h2>").append(studentName).append(" 同学的成绩单</h2>");
        sb.append("<table border='1' cellpadding='5' cellspacing='0' style='border-collapse: collapse;'>");
        sb.append("<tr style='background-color: #f0f0f0;'>");
        sb.append("<th>课程名称</th><th>学分</th><th>成绩</th><th>绩点</th><th>类型</th>");
        sb.append("</tr>");

        double totalCredits = 0;
        double totalPoints = 0;

        for (Course score : scores) {
            String type = score.getType() != null && score.getType() == 1 ? "必修" : "选修";
            sb.append("<tr>");
            sb.append("<td>").append(score.getName() != null ? score.getName() : "").append("</td>");
            sb.append("<td>").append(score.getCreditsByUser() != null ? score.getCreditsByUser() : "0").append("</td>");
            sb.append("<td>").append(score.getScoreByUser() != null ? score.getScoreByUser() : "未录入").append("</td>");
            sb.append("<td>").append(score.getPointByUser() != null ? score.getPointByUser() : "0").append("</td>");
            sb.append("<td>").append(type).append("</td>");
            sb.append("</tr>");

            if (score.getCreditsByUser() != null) {
                totalCredits += Double.parseDouble(score.getCreditsByUser().toString());
            }
            if (score.getPointByUser() != null) {
                totalPoints += Double.parseDouble(score.getPointByUser().toString());
            }
        }

        sb.append("</table>");
        sb.append("<p>总学分: <strong>").append(totalCredits).append("</strong></p>");
        sb.append("<p>平均绩点: <strong>").append(totalCredits > 0 ? String.format("%.2f", totalPoints / scores.size()) : "0").append("</strong></p>");
        sb.append("<hr>");
        sb.append("<p style='color: #666; font-size: 12px;'>此邮件由瀚海大学学生成绩管理系统自动发送</p>");
        sb.append("</body></html>");

        return sb.toString();
    }
}
