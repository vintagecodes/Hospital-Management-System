package com.appoint.service;

import java.io.InvalidClassException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.appoint.exception.CustomException;
import com.appoint.model.Appointment;
import com.appoint.model.Doctor;
import com.appoint.model.JwtResponse;
import com.appoint.model.Patient;
import com.appoint.repository.AppointRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppointmentService {
	
//	private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	
	private static String SECRET = "JG5aH2Fb7cZlI9XpDtEjOvKu4nQ6rWx0YsRgTm1oUyLkC3qVwB8PzMiNfSdAhVefff357638792F423F4428472B4B6250655368566D597133743677397A2443264629";
//	

	@Autowired
	private AppointRepository appointRepository;
	
	@Autowired
	private SequenceGeneratorService generatorService;
	
	@Autowired
	private JmsTemplate template;
	
	@Autowired
	private RestTemplate restTemplate;
	
	 @Value("${jwtSecret}")
	 private String secretKey; 
	
//	private StringBuilder latestAppointmentMessage = new StringBuilder(); 
	private String latestAppointmentMessage = "";
	
	public ResponseEntity<?> createAppointment(String doctorId, String name, Appointment appoint) throws CustomException, InvalidClassException{
		Appointment app = new Appointment();
		JwtResponse jwtResponse = restTemplate.getForObject("http://localhost:8500/api/auth/logonDetails/"+name, JwtResponse.class);
		log.info("JwtResponse Details: "+jwtResponse.getUserId());
		if(jwtResponse.getRoles().get(0).equals("ROLE_PATIENT"))
		{
			log.info("JwtResponse Details: "+jwtResponse.getToken());
			if(validateTokenFromExternalService(jwtResponse.getToken(),jwtResponse.getUsername()))
			{
				try {
					Doctor doctor = restTemplate.getForObject("http://localhost:5000/doctor/getById/"+doctorId, Doctor.class);
//					System.out.println("Doctor "+doctor);
					Patient patient = restTemplate.getForObject("http://localhost:5001/patient/getByNameObj/"+name, Patient.class);
//					System.out.println("Patient "+patient);
					if(doctor!=null && patient!=null) {
						app.setAppointId(String.valueOf(generatorService.generateSequence(Appointment.SEQUENCE_NAME)));
						app.setUsername(name);
						app.setDoctor(doctor);
						app.setPatient(patient);
						app.setCause(appoint.getCause());
						app.setStatus(appoint.getStatus());
						app.setPaymentStatus(appoint.getPaymentStatus());
						app.setSchedule(appoint.getSchedule());
						app.setTotalFees(doctor.getFees());
					}
					  ObjectMapper objectMapper = new ObjectMapper();
					  String message = objectMapper.writeValueAsString(app);
					template.convertAndSend("appointment-message", message);
					appointRepository.save(app);
					return ResponseEntity.ok(app);
				}catch (Exception e) {
		            throw new InvalidClassException("Error creating rental: " + e.getMessage());
		        }
			}
			else
			{
				return ResponseEntity.internalServerError().body("Token Expired");
			}
		}
		else
		{
			return ResponseEntity.status(401).body("UNAUTHORIZED");
		}
		
	}
	
	public Appointment getAppointmentById(String appointId) throws CustomException{
		Appointment app1 = appointRepository.findByAppointId(appointId);
//		System.out.println("List :"+app1);
		if(app1==null) {
			throw new CustomException("Invalid ID");
		}
		return app1;
		
	}
	
	public List<Appointment> getAppointByUsername(String username) throws CustomException{
		
		List<Appointment> getU = appointRepository.findByUsername(username);
//		System.out.println("List :"+getU);
		if(getU.isEmpty()) {
			throw new CustomException("Invalid Username");
		}
		return getU;
	}
	
	public List<Appointment> getAppointByDoctorUsername(String username) throws CustomException{
		
		List<Appointment> getU = findByUsername(username);
//		System.out.println("List :"+getU);
		if(getU.isEmpty()) {
			throw new CustomException("Invalid Username");
		}
		return getU;
	}
	
	private List<Appointment> findByUsername(String username) {
		// TODO Auto-generated method stub
		List<Appointment> newCreatedList = new ArrayList<>();
		List<Appointment> getAppointmentDetailsPerDoctor=appointRepository.findAll();
		for(Appointment b:getAppointmentDetailsPerDoctor) {
			if(b.getDoctor().getName().equals(username)) {
				newCreatedList.add(b);
				
			}
		}
		return newCreatedList;
	}
	
	public List<Appointment> getAppointList() throws CustomException{
		List<Appointment> getU = appointRepository.findAll();
//		System.out.println("List :"+getU);
		if(getU.isEmpty()) {
			throw new CustomException("Something went Wrong!");
		}
		return getU;
	}
	
	public Appointment updatePaymentStatus(String appointId, Appointment appointment) throws CustomException{
		Appointment app1 = appointRepository.findByAppointId(appointId);
		app1.setPaymentStatus(appointment.getPaymentStatus());
		return appointRepository.save(app1);
	}
	
	public Appointment updateStatus(String appointId, Appointment appointment) throws CustomException{
		Appointment app1 = appointRepository.findByAppointId(appointId);
		app1.setStatus(appointment.getStatus());
		return appointRepository.save(app1);
	}
	
	@JmsListener(destination = "appointment-message")
	public void setLatestAppointmentMessage(String latestAppointmentMessage)
	{
		System.out.println("The message was: "+latestAppointmentMessage);
		this.latestAppointmentMessage = latestAppointmentMessage;
		System.out.println("Appointment Message1: "+this.latestAppointmentMessage);
	}
	
	public String getLatestAppointmentMessage()
	{
		System.out.println("Appointment Message2: "+latestAppointmentMessage);
		return latestAppointmentMessage;
	}
	
	public boolean validateTokenFromExternalService(String token, String userName) {
        boolean isValid = validateToken(token,userName);
        System.out.println("Output validate "+isValid);
        if (isValid) {
            return true;
        } else {
        	return false;
        }
    }
	
	 // Method to verify and extract claims from a JWT token
    public static boolean verifyToken(String token, String username) throws SignatureException, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
    	Jws<Claims> claimsJws = null;
		try {
			claimsJws = Jwts.parser()
			        .setSigningKey(getSignKey())
			        .parseClaimsJws(token);
		} catch (SignatureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExpiredJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedJwtException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Token OKK "+claimsJws.getBody());
		
		return verifyUserFromToken(claimsJws,username);
    }
    
    public static boolean verifyUserFromToken(Jws<Claims> claims, String username)
    {
    	log.info("Username when login: "+username);
    	log.info("Username from Token"+claims.getBody().get("sub").toString());
    	log.info("Verify boolean returns "+claims.getBody().get("sub").toString().equals(username));
    	if(claims.getBody().get("sub").toString().equals(username))
		{
			return true;
		}
    	else
    	{
    		return false;
    	}
    }
	
	public boolean validateToken(String token, String username) {
       if(isTokenValid(token, username))
       {
    	   return true;
       }
       else
       {
    	   return false;
       }
    }
	
	public static boolean isTokenValid(String token, String username)
	{
		final boolean validity = verifyToken(token,username);
		log.info("IsTokenValid: "+validity);
		return validity;
	}
	
//	 public static boolean isTokenExpired(String token, String username) {
//	        final Date expiration = extractExpiration(token,username);
//	        log.info("expiration: "+ expiration);
//	        return expiration.before(new Date());
//	    }
	 
	// Method to extract expiration date from a token
//	    public static Date extractExpiration(String token,String username) {
//	        return ((ResponseEntity<Claims>) verifyToken(token,username)).getExpiration();
//	    }
	    
		private static Key getSignKey() {
	        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
	        return Keys.hmacShaKeyFor(keyBytes);
	    }
}
