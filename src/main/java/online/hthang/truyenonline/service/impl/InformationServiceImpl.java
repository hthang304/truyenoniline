package online.hthang.truyenonline.service.impl;

import online.hthang.truyenonline.entity.Information;
import online.hthang.truyenonline.repository.InformationRepository;
import online.hthang.truyenonline.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Huy Thang
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationRepository informationRepository;

    @Override
    public Information getWebInfomation() {
        return informationRepository.findFirstByOrderByInfoIDDesc();
    }

}
