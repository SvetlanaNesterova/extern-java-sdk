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

package ru.kontur.extern_api.sdk.utils.builders;

import java.util.UUID;
import ru.kontur.extern_api.sdk.ExternEngine;
import ru.kontur.extern_api.sdk.model.DraftMetaRequest;
import ru.kontur.extern_api.sdk.model.RelatedDocumentRequest;
import ru.kontur.extern_api.sdk.model.TestData;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilder;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilderData;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilderMetaRequest;
import ru.kontur.extern_api.sdk.utils.CryptoUtils;
import ru.kontur.extern_api.sdk.utils.TestUtils;

public class DraftsBuilderCreator {

    /**
     * ДО на который отвечаем - требование созданное в облаке
     */
    public FnsInventoryDraftsBuilder createFnsInventoryDraftsBuilder(
            ExternEngine engine,
            CryptoUtils cryptoUtils
    ) {
        String certificate = cryptoUtils.loadX509(engine.getConfiguration().getThumbprint());
        TestData[] testData = TestUtils.getTestData(certificate);

        DraftMetaRequest draftMeta = TestUtils.toDraftMetaRequest(testData[0]);
        FnsInventoryDraftsBuilderMetaRequest meta = new FnsInventoryDraftsBuilderMetaRequest();
        meta.setPayer(draftMeta.getPayer());
        meta.setSender(draftMeta.getSender());
        meta.setRecipient(draftMeta.getRecipient());

        RelatedDocumentRequest relatedDocument = new RelatedDocumentRequest();
        relatedDocument.setRelatedDocflowId(UUID.fromString("06be6410-c694-446e-8add-8787460e400b"));
        relatedDocument.setRelatedDocumentId(UUID.fromString("88543b8d-41e5-48b8-894f-9f7bed8d565b"));

        FnsInventoryDraftsBuilderData data = new FnsInventoryDraftsBuilderData();
        data.setRelatedDocument(relatedDocument);
        meta.setBuilderData(data);

        return engine
                .getDraftsBuilderService()
                .fnsInventory()
                .createAsync(meta)
                .join();
    }
}
