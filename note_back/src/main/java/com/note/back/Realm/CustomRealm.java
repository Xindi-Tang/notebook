package com.note.back.Realm;

import com.note.back.pojo.User;
import com.note.back.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException{
        String userName=token.getPrincipal().toString();//身份认证令牌，死的写法都这么写
        User user=userService.getUserByUsername(userName);
        String passsword=user.getPassword();
        String salt=user.getSalt();
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user,passsword, ByteSource.Util.bytes(salt),getName());
        return authenticationInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection){
        return null;
    }
}
