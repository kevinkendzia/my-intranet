package de.kkendzia.myintranet.microstream._framework;

import de.kkendzia.myintranet.domain.user.*;
import de.kkendzia.myintranet.microstream.MyIntranetRoot;
import one.microstream.integrations.spring.boot.types.config.StorageManagerInitializer;
import one.microstream.storage.types.StorageManager;
import org.springframework.stereotype.Component;

@Component
public class EIStorageManagerInitializer implements StorageManagerInitializer
{
    @Override
    public void initialize(StorageManager sm)
    {
        MyIntranetRoot root = (MyIntranetRoot) sm.root();
        if(!root.isInit())
        {
            EIUser u = new EIUser();
            u.setId(1);
            u.setUserName("root");
            u.setPassword("root");
            root.getEIUsers().add(u);
            sm.store(root.getEIUsers());

            Permission pRoot = new Permission();
            pRoot.setId(1);
            pRoot.setName("root");
            root.getPermissions().add(pRoot);

            Permission pAhRead = new Permission();
            pAhRead.setId(2);
            pAhRead.setName("read_ah");
            root.getPermissions().add(pAhRead);

            Permission pAhWrite = new Permission();
            pAhWrite.setId(3);
            pAhWrite.setName("write_ah");
            root.getPermissions().add(pAhWrite);

            sm.store(root.getPermissions());

            Role rRoot = new Role();
            rRoot.setId(1);
            rRoot.setName("root");
            root.getRoles().add(rRoot);
            sm.store(root.getRoles());

            RolePermission rpRoot = new RolePermission();
            rpRoot.setId(1);
            rpRoot.setPermissionId(1);
            rpRoot.setRoleId(1);
            root.getRolePermissions().add(rpRoot);
            sm.store(root.getRolePermissions());

            UserRole urRoot = new UserRole();
            urRoot.setId(1);
            urRoot.setUserId(1);
            urRoot.setRoleId(1);
            root.getUserRoles().add(urRoot);
            sm.store(root.getUserRoles());

            root.setInit(true);
            sm.storeRoot();
        }
    }
}
