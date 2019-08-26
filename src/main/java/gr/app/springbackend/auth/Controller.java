package gr.app.springbackend.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gr.app.springbackend.bean.AuthenticationBean;
import gr.app.springbackend.db.entity.UserEntity;
import gr.app.springbackend.db.repo.UserRepo;
import gr.app.springbackend.model.LoginModel;
import gr.app.springbackend.model.RegisterModel;
import gr.app.springbackend.service.UserServiceJWT;

@RestController
@RequestMapping(path = "/auth", consumes = "application/json", produces = "application/json")
public class Controller {

    @Autowired
    UserRepo userRepo;

    @Autowired
    UserServiceJWT userServiceJWT;

    @PostMapping
    @ResponseBody
    public ResponseEntity<AuthenticationBean> authenticateUser(@RequestBody LoginModel loginModel) {
        try{
            return new ResponseEntity<AuthenticationBean>(
                userServiceJWT.authenticateUser(
                    loginModel.getUsername(),
                    loginModel.getPassword()),
                HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<AuthenticationBean>(HttpStatus.FORBIDDEN);  
        }
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<UserEntity>> getUsers() {
        System.out.println(userRepo.findAll());
        return new ResponseEntity<List<UserEntity>>(userRepo.findAll(),HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<HttpStatus> registerUser(@RequestBody RegisterModel registerModel) {
        userRepo.save(registerModel.getUserEntity(registerModel));
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userRepo.deleteById(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}