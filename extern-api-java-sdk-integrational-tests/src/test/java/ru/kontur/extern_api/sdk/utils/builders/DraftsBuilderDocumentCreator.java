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

import ru.kontur.extern_api.sdk.ExternEngine;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilder;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilderDocument;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilderDocumentData;
import ru.kontur.extern_api.sdk.model.builders.fns_inventory.FnsInventoryDraftsBuilderDocumentMetaRequest;

public class DraftsBuilderDocumentCreator {

    public FnsInventoryDraftsBuilderDocument createFnsInventoryDraftsBuilderDocument(
            ExternEngine engine,
            FnsInventoryDraftsBuilder draftsBuilder
    ) {
        // Пункт требования, подходящий по формату. Он нужен ФНС для понимания того, на какую часть требования пришел документ.
        final String claimItemNumber = "1.01";

        FnsInventoryDraftsBuilderDocumentMetaRequest meta = new FnsInventoryDraftsBuilderDocumentMetaRequest();
        FnsInventoryDraftsBuilderDocumentData data = new FnsInventoryDraftsBuilderDocumentData();
        data.setClaimItemNumber(claimItemNumber);
        meta.setBuilderData(data);

        return engine
                .getDraftsBuilderService()
                .fnsInventory()
                .getDocumentService(draftsBuilder.getId())
                .createAsync(meta)
                .join();
    }
}
