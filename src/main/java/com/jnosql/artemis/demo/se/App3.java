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


import org.eclipse.jnosql.artemis.DatabaseQualifier;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import java.util.Collections;

public class App3 {



    public static void main(String[] args) {

        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {
            FileObject ironMan = FileObject.builder().withRealName("Tony Stark").withName("iron_man")
                    .withAge("34").withPowers(Collections.singleton("rich")).build();

            FileRepository repository = container.select(FileRepository.class, DatabaseQualifier.ofDocument()).get();
            repository.save(ironMan);

            System.out.println(repository.findByName("iron_man"));
            System.out.println(repository.findByAgeGreaterThan(30));
            System.out.println(repository.findByAgeLessThan(40));

        }
    }

    private App3() {
    }
}
