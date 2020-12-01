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

import java.util.Collections;
import java.util.Set;

public class FileBuilder {


    private String name;

    private String realName;

    private String age;

    private Set<String> powers = Collections.emptySet();


    public FileBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public FileBuilder withRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public FileBuilder withAge(String age) {
        this.age = age;
        return this;
    }

    public FileBuilder withPowers(Set<String> powers) {
        this.powers = powers;
        return this;
    }

    public FileObject build() {
        return new FileObject(name, realName, age, powers);
    }
}