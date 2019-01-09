package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.repository.RoleRepository;
import online.hthang.truyenonline.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Huy Thang
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

}
