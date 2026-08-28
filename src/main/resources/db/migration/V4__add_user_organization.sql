CREATE TABLE user_organization (
                                    user_id UUID NOT NULL,
                                    org_id UUID NOT NULL,
                                    CONSTRAINT pk_user_organization PRIMARY KEY (user_id, org_id),
                                    CONSTRAINT fk_user_organization_user FOREIGN KEY (user_id)
                                        REFERENCES app_user (user_id),
                                    CONSTRAINT fk_user_organization_org FOREIGN KEY (org_id)
                                        REFERENCES organization (org_id)
);
