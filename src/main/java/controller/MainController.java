package controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class MainController {
	
    @GetMapping(value = "/api/secure/request/{value}")
    public ResponseEntity<?> secureRequest(@PathVariable String value, Principal p) {
    	
        String result = String.format("'%s' came from a secure channel.", value);
        return ResponseEntity.ok(result);
    }
 
    @GetMapping(value = "/api/public/request/{value}")
    public ResponseEntity<?> insecureRequest(@PathVariable String value) {
    	
        String result = String.format("'%s' came from a INsecure channel.", value);
        return ResponseEntity.ok(result);
    }
}
