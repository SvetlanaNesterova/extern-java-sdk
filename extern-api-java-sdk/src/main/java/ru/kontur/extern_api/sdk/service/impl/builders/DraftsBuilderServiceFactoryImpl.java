/*
 * Copyright (c) 2019 SKB Kontur
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package ru.kontur.extern_api.sdk.service.impl.builders;

import ru.kontur.extern_api.sdk.httpclient.api.builders.fns_inventory.FnsInventoryDraftsBuilderDocumentFilesApi;
import ru.kontur.extern_api.sdk.httpclient.api.builders.fns_inventory.FnsInventoryDraftsBuilderDocumentsApi;
import ru.kontur.extern_api.sdk.httpclient.api.builders.fns_inventory.FnsInventoryDraftsBuildersApi;
import ru.kontur.extern_api.sdk.provider.AccountProvider;
import ru.kontur.extern_api.sdk.service.builders.DraftsBuilderServiceFactory;
import ru.kontur.extern_api.sdk.service.builders.fns_inventory.FnsInventoryDraftsBuilderService;
import ru.kontur.extern_api.sdk.service.impl.builders.fns_inventory.FnsInventoryDraftsBuilderServiceImpl;

public class DraftsBuilderServiceFactoryImpl implements DraftsBuilderServiceFactory {

    private final AccountProvider acc;
    private final FnsInventoryDraftsBuildersApi fnsInventoryDraftsApi;
    private final FnsInventoryDraftsBuilderDocumentsApi fnsInventoryDocumentApi;
    private final FnsInventoryDraftsBuilderDocumentFilesApi fnsInventoryFileApi;

    public DraftsBuilderServiceFactoryImpl(
            AccountProvider accountProvider,
            FnsInventoryDraftsBuildersApi fnsInventoryDraftsApi,
            FnsInventoryDraftsBuilderDocumentsApi fnsInventoryDocumentApi,
            FnsInventoryDraftsBuilderDocumentFilesApi fnsInventoryFileApi
    ) {
        this.acc = accountProvider;
        this.fnsInventoryDraftsApi = fnsInventoryDraftsApi;
        this.fnsInventoryDocumentApi = fnsInventoryDocumentApi;
        this.fnsInventoryFileApi = fnsInventoryFileApi;
    }

    @Override
    public FnsInventoryDraftsBuilderService fnsInventory() {
        return new FnsInventoryDraftsBuilderServiceImpl(
                acc,
                fnsInventoryDraftsApi,
                fnsInventoryDocumentApi,
                fnsInventoryFileApi
        );
    }
}
