/*
 * Copyright (c) 2019, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ballerinalang.langserver.codelenses;

import org.ballerinalang.langserver.compiler.LSContext;
import org.ballerinalang.util.diagnostic.Diagnostic;
import org.wso2.ballerinalang.compiler.tree.BLangCompilationUnit;
import org.wso2.ballerinalang.compiler.tree.BLangPackage;

import java.util.List;

/**
 * Keys associated to execute code lenses operations.
 *
 * @since 0.990.3
 */
public class CodeLensesProviderKeys {

    public static final LSContext.Key<BLangCompilationUnit> COMPILATION_UNIT_KEY = new LSContext.Key<>();
    public static final LSContext.Key<BLangPackage> BLANG_PACKAGE_KEY = new LSContext.Key<>();
    public static final LSContext.Key<String> FILE_URI_KEY = new LSContext.Key<>();
    public static final LSContext.Key<List<Diagnostic>> DIAGNOSTIC_KEY = new LSContext.Key<>();

    private CodeLensesProviderKeys() {
    }
}
