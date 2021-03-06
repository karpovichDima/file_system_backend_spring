package com.dazito.oauthexample.service.impl;

import com.dazito.oauthexample.model.AccountEntity;
import com.dazito.oauthexample.model.Organization;
import com.dazito.oauthexample.model.StorageElement;
import com.dazito.oauthexample.model.type.ResponseCode;
import com.dazito.oauthexample.model.type.UserRole;
import com.dazito.oauthexample.service.UtilService;
import com.dazito.oauthexample.utils.exception.AppException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
public class UtilServiceImpl implements UtilService {

    @Override
    public boolean matchesOrganizations(Organization organizationUser, Organization organizationStorage) {
        String organizationNameUser = organizationUser.getOrganizationName();
        String organizationNameFile = organizationStorage.getOrganizationName();
        return organizationNameUser.equals(organizationNameFile);
    }

    @Override
    public boolean matchesOwner(Long idCurrent, Long ownerId) {
        return Objects.equals(idCurrent, ownerId);
    }

    @Override
    public boolean isPermissionsAdminOrUserIsOwner(AccountEntity currentUser, AccountEntity owner, StorageElement foundFile) {
        UserRole role = currentUser.getRole();
        Long idUser = currentUser.getId();
        boolean checkedOnTheAdmin = adminRightsCheck(currentUser);
        if (!checkedOnTheAdmin) {
            boolean checkedMatchesOwner = matchesOwner(idUser, owner.getId());
            if (!checkedMatchesOwner) return false;
        }
        if (role.equals(UserRole.ADMIN) && owner == null){
            return true;
        }
        return true;
    }

    private boolean adminRightsCheck(AccountEntity currentUser) {
        UserRole role = currentUser.getRole();
        return role == UserRole.ADMIN;
    }

    @Override
    public boolean checkPermissionsOnChangeByOrganization(AccountEntity currentUser, StorageElement foundFile) {
        Organization organizationUser = currentUser.getOrganization();
        Organization organizationFile = foundFile.getOrganization();
        boolean checkedMatchesOrganization = matchesOrganizations(organizationUser, organizationFile);
        if (!checkedMatchesOrganization) return false;
        return true;
    }

    @Override
    public File createSinglePath(String path) {
        File rootPath = new File(path);
        if (!rootPath.exists()) {
            if (rootPath.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        return rootPath;
    }

    @Override
    public File createMultiplyPath(String path) {
        File rootPath2 = new File(path + "\\Directory\\Sub\\Sub-Sub");
        if (!rootPath2.exists()) {
            if (rootPath2.mkdirs()) {
                System.out.println("Multiple directories are created!");
            } else {
                System.out.println("Failed to create multiple directories!");
            }
        }
        return rootPath2;
    }

    @Override
    public void isMatchesOrganization(@NonNull String organizationName1, @NonNull String organizationName2) throws AppException {
        boolean equals = organizationName1.equals(organizationName2);
        if (!equals)
            throw new AppException("You are trying to access content not from your organization.", ResponseCode.ORGANIZATIONS_NOT_MUCH);
    }
}
