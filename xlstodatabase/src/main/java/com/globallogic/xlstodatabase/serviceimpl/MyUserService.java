/*
package com.globallogic.xlstodatabase.serviceimpl;

import com.globallogic.xlstodatabase.config.MyUserDetails;
import com.globallogic.xlstodatabase.modal.MyUser;
import com.globallogic.xlstodatabase.repository.MyUserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MyUserService implements UserDetailsService {
    @Autowired
    MyUserRep myUserRep;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> userOptional = myUserRep.findByUsername(username);
        return userOptional.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
*/
