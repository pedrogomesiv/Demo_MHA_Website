package com.nsa.mhasite.email;

import com.nsa.mhasite.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailSessionBean{
    @Autowired
    public JavaMailSender emailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public void welcomeEmail(String address, String password, String name){
        String message = "Welcome to the MHA Volunteering Service "+name+"! \n\n";
        message += "Your temporary password is: ";
        message += password;
        message += "\n\nPlease visit http://localhost:8081/profile/details to change it";
        sendEmail(address, "Welcome to the MHA volunteering service!", message);
    }

    public void redemptionEmailUser(User user, Offer offer, VolunteerUser volunteerUser, String code){
        String message = "Hi "+volunteerUser.getFirstName()+"! \n\n";
        message += "Congratulations, you have redeemed: "+offer.getName()+"\n\n";
        message += "Your redemption code is : "+code+"\n\n";
        message += "Please show this code to the business to receive your reward\n\n";
        message += "Kind regards\n";
        message += "Monmouthshire Housing Association";
        sendEmail(user.getUsername(), "Your reward is ready!", message);
    }

    public void redemptionEmailBusiness(Business business, Offer offer, VolunteerUser volunteerUser, String code){
        String message = business.getBname() + "\n\n";
        message += volunteerUser.getFirstName() + " " + volunteerUser.getLastName() + " has redeemed your offer: " + offer.getName() + "\n\n";
        message += "Their unique redemption code is: "+code+"\n\n";
        message += "Kind regards\n";
        message += "Monmouthshire Housing Association";
        sendEmail(business.getBusinessEmail(), "MHA offer redeemed by " + volunteerUser.getFirstName() + " " + volunteerUser.getLastName(), message);
    }

    public void achievementEmail(User user, VolunteerUser volunteerUser, Achievement achievement){
        String message = "Hi "+volunteerUser.getFirstName()+"! \n\n";
        message += "Congratulations, you have earned the Achievement: "+achievement.getName()+"\n";
        message += achievement.getDescription() + "\n\n";
        message += "Keep up the good work!\n\n";
        message += "Kind regards\n";
        message += "Monmouthshire Housing Association";
        sendEmail(user.getUsername(), "New Achievement Earned!", message);
    }
    public void giftEmail(GiftForm giftForm, VolunteerUser volunteerUser, Offer offer, String code){
        String message = "Hi "+giftForm.getName()+"! \n\n";
        message += "You have received a gift from "+volunteerUser.getFirstName()+"\n\n";
        message += giftForm.getMessage() + "\n\n";
        message += "They have sent you the offer :"+offer.getName()+"\n\n";
        message += "Your redemption code is : "+code+"\n\n";
        message += "Please show this code to the business to receive your reward\n\n";
        message += "Kind regards\n";
        message += "Monmouthshire Housing Association";
        sendEmail(giftForm.getEmail1(), "Gift received from "+volunteerUser.getFirstName()+"!", message);
    }

    public void giftEmailBusiness(GiftForm giftForm, Business business, Offer offer, VolunteerUser volunteerUser, String code){
        String message = business.getBname() + "\n\n";
        message += giftForm.getName() + " has redeemed your offer: " + offer.getName() + "\n\n";
        message += "Their unique redemption code is: "+code+"\n\n";
        message += "Kind regards\n";
        message += "Monmouthshire Housing Association";
        sendEmail(business.getBusinessEmail(), "MHA offer redeemed by " + giftForm.getName(), message);
    }

    public void giftEmailSender(GiftForm giftForm, User user, VolunteerUser volunteerUser, Offer offer){
        String message = "Hi "+volunteerUser.getFirstName()+"! \n\n";
        message += "Congratulations, you have successfully gifted: "+offer.getName()+"\n\n";
        message += "To : "+giftForm.getName()+"\n\n";
        message += "They have received an email with their unique code\n\n";
        message += "Kind regards\n";
        message += "Monmouthshire Housing Association";
        sendEmail(user.getUsername(), "Your gift has been sent!", message);
    }
}