/*
 * Copyright (c) 2017 Otávio Santana and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Apache License v2.0 is available at http://www.opensource.org/licenses/apache2.0.php.
 *
 * You may elect to redistribute this code under either of these licenses.
 *
 * Contributors:
 *
 * Otavio Santana
 */
package com.jnosql.artemis.demo.se;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
public class FileObject implements Serializable {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String realName;

    @Column
    private String number;

    @Column
    private Set<String> properties;

    FileObject() {
    }

    FileObject(String name, String realName, String age, Set<String> powers) {
        this.id = name;
        this.name = name;
        this.realName = realName;
        this.number = age;
        this.properties = powers;
    }


    public String getName() {
        return name;
    }

    public String getRealName() {
        return realName;
    }

    public String getAge() {
        return number;
    }

    public Set<String> getPowers() {
        if (properties == null) {
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(properties);
    }

    public static FileBuilder builder() {
        return new FileBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileObject)) {
            return false;
        }
        FileObject hero = (FileObject) o;
        return Objects.equals(name, hero.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hero{");
        sb.append("name='").append(name).append('\'');
        sb.append(", realName='").append(realName).append('\'');
        sb.append(", age=").append(number);
        sb.append(", powers=").append(properties);
        sb.append('}');
        return sb.toString();
    }
}