/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.picketlink.authorization.idm.jpa;

import org.picketlink.Identity;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.RelationshipManager;
import org.picketlink.idm.model.basic.BasicModel;
import org.picketlink.idm.model.basic.Group;
import org.picketlink.idm.model.basic.Role;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import static org.picketlink.idm.model.basic.BasicModel.getGroup;
import static org.picketlink.idm.model.basic.BasicModel.getRole;
import static org.picketlink.idm.model.basic.BasicModel.hasRole;

/**
 * This is a utility bean that may be used by the view layer to determine whether the
 * current user has specific privileges. 
 * 
 * @author Shane Bryzak
 *
 */
@Named
@Stateless
public class AuthorizationChecker {
    
    @Inject
    private Identity identity;
    
    @Inject 
    private IdentityManager identityManager;

    @Inject
    private RelationshipManager relationshipManager;

    public boolean hasApplicationRole(String roleName) {
        Role role = getRole(this.identityManager, roleName);
        return hasRole(this.relationshipManager, this.identity.getAccount(), role);
    }

    public boolean isMember(String groupName) {
        Group group = getGroup(this.identityManager, groupName);
        return BasicModel.isMember(this.relationshipManager, this.identity.getAccount(), group);
    }

    public boolean hasGroupRole(String roleName, String groupName) {
        Group group = getGroup(this.identityManager, groupName);
        Role role = getRole(this.identityManager, roleName);
        return BasicModel.hasGroupRole(this.relationshipManager, this.identity.getAccount(), role, group);
    }
}
