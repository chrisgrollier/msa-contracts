/*
 * Creation : 24 Jun 2019
 */
package net.chrisgrollier.cloud.apps.sample.contract;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import net.chrisgrollier.cloud.apps.sample.contract.dao.ContractDAO;
import net.chrisgrollier.cloud.apps.sample.contract.entity.ContractEntity;
import net.chrisgrollier.cloud.apps.sample.contract.entity.ContractType;

/**
 * Used to initialise contract repository. For this sample, we used memory database (H2 database)
 */
@Component
public class DataInit implements ApplicationRunner {
    private final ContractDAO contractDAO;

    @Autowired
    public DataInit(ContractDAO contractDAO) {
        this.contractDAO = contractDAO;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        long count = contractDAO.count();

        if (count == 0) {
            final ContractEntity contact1 = new ContractEntity();
            contact1.setType(ContractType.LOA);
            contact1.setDuration(36);
            contact1.setPrice(350D);
            contact1.setUserId(1);

            final ContractEntity contact2 = new ContractEntity();
            contact2.setType(ContractType.LLD);
            contact2.setDuration(24);
            contact2.setPrice(750D);
            contact2.setUserId(2);

            final ContractEntity contact3 = new ContractEntity();
            contact3.setType(ContractType.VAC);
            contact3.setDuration(36);
            contact3.setPrice(500D);
            contact3.setUserId(3);

            contractDAO.save(contact1);
            contractDAO.save(contact2);
            contractDAO.save(contact3);
        }

    }

}
