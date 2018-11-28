package ru.kontur.extern_api.sdk.utils;

import java.util.UUID;
import ru.kontur.extern_api.sdk.ExternEngine;
import ru.kontur.extern_api.sdk.adaptor.QueryContext;
import ru.kontur.extern_api.sdk.model.DocumentContents;
import ru.kontur.extern_api.sdk.model.Draft;
import ru.kontur.extern_api.sdk.model.DraftDocument;
import ru.kontur.extern_api.sdk.model.DraftMeta;
import ru.kontur.extern_api.sdk.model.FnsRecipient;
import ru.kontur.extern_api.sdk.model.TestData;

public class DraftTestPack {

    private final TestData data;
    private final DraftMeta meta;
    private final ExternEngine engine;
    private final CryptoUtils cryptoUtils;

    private QueryContext<UUID> createDefaultDraftCxt;

    public DraftTestPack(TestData data, ExternEngine engine, CryptoUtils cryptoUtils) {

        this.cryptoUtils = cryptoUtils;
        this.engine = engine;
        this.data = data;
        meta = TestUtils.toDraftMeta(data);
    }

    public void createNewEmptyDraftIfNecessary() {

        QueryContext<Draft> draft = getDraft();

        if (createDefaultDraftCxt == null
                || draft.getServiceError() != null
                || draft.get().getStatus().name().equals("sent")) {
            createNewEmptyDraft();
        }
    }

    private QueryContext<UUID> getCreateDraftCxt() {

        if (getDraft().get().getDocuments().size() > 0) {
            createNewEmptyDraft();
        }
        return createDefaultDraftCxt;
    }

    public void createNewEmptyDraft() {

        createDefaultDraftCxt = UncheckedSupplier.get(() -> engine.getDraftService()
                .createAsync(meta)
                .get()
                .ensureSuccess())
                .map(QueryContext.DRAFT_ID, Draft::getId);
    }

    private QueryContext<Draft> getEmptyDraft() {

        QueryContext<Draft> getDraftCxt = getDraft();

        if (getDraftCxt.get().getDocuments().size() > 0) {
            createNewEmptyDraft();
            return getDraft();
        }
        return getDraftCxt;
    }

    private QueryContext<Draft> getDraft() {

        return UncheckedSupplier.get(() -> engine.getDraftService()
                .lookupAsync(createDefaultDraftCxt.get())
                .get());
    }

    private QueryContext<DraftDocument> addDocument() {

        String path = data.getDocs()[0];
        DocType docType = DocType.getDocType(meta.getRecipient());
        DocumentContents documentContents = EngineUtils.with(engine)
                .createDocumentContents(path, docType);

        return UncheckedSupplier.get(() -> engine
                .getDraftService()
                .addDecryptedDocumentAsync(createDefaultDraftCxt.get(), documentContents)
                .get()
                .ensureSuccess());
    }

    private void signDocument(UUID documentId) {

        byte[] docContent = UncheckedSupplier.get(() -> engine.getDraftService()
                .getDecryptedDocumentContentAsync(
                        createDefaultDraftCxt.get(),
                        documentId)
                .get()
                .ensureSuccess()
                .get());

        byte[] signature = cryptoUtils
                .sign(engine.getConfiguration().getThumbprint(), Zip.unzip(docContent));

        UncheckedSupplier.get(() -> engine.getDraftService()
                .updateSignatureAsync(
                        createDefaultDraftCxt.get(),
                        documentId,
                        signature)
                .get());
    }

    public QueryContext<UUID> createDraftCxtFactory() {
        return getCreateDraftCxt();
    }

    public QueryContext<Draft> emptyDraftCxtFactory() {
        return getEmptyDraft();
    }

    public QueryContext<Draft> draftWithDocumentCxtFactory() {
        addDocument();
        return getDraft();

    }

    public QueryContext<Draft> newDraftWithDocumentCxtFactory() {
        createNewEmptyDraft();
        addDocument();
        return getDraft();
    }

    public QueryContext<Draft> draftWithSignedDocumentCxtFactory() {
        DraftDocument document = addDocument().get();
        signDocument(document.getId());
        return getDraft();
    }

    public Pair<QueryContext<Draft>, QueryContext<DraftDocument>> addDocumentPackFactory() {
        return new Pair<>(
                getEmptyDraft(), addDocument());
    }

    public Pair<QueryContext<Draft>, QueryContext<DraftDocument>> addDocumentNoFnsPackFactory() {
        if (!(meta.getRecipient() instanceof FnsRecipient)) {
            return null;
        }

        return new Pair<>(
                getEmptyDraft(), addDocument());

    }
}
