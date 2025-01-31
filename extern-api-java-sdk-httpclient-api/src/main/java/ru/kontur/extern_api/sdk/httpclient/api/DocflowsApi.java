/*
 * Copyright (c) 2018 SKB Kontur
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
 *
 */

package ru.kontur.extern_api.sdk.httpclient.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import ru.kontur.extern_api.sdk.GsonProvider;
import ru.kontur.extern_api.sdk.adaptor.ApiResponse;
import ru.kontur.extern_api.sdk.httpclient.ApiResponseConverter;
import ru.kontur.extern_api.sdk.httpclient.JsonSerialization;
import ru.kontur.extern_api.sdk.httpclient.LibapiResponseConverter;
import ru.kontur.extern_api.sdk.httpclient.Raw;
import ru.kontur.extern_api.sdk.model.CertificateContent;
import ru.kontur.extern_api.sdk.model.DecryptInitiation;
import ru.kontur.extern_api.sdk.model.Docflow;
import ru.kontur.extern_api.sdk.model.DocflowDocumentDescription;
import ru.kontur.extern_api.sdk.model.DocflowFilter;
import ru.kontur.extern_api.sdk.model.DocflowPage;
import ru.kontur.extern_api.sdk.model.Document;
import ru.kontur.extern_api.sdk.model.ByteContent;
import ru.kontur.extern_api.sdk.model.ReplyDocument;
import ru.kontur.extern_api.sdk.model.SenderIp;
import ru.kontur.extern_api.sdk.model.SignConfirmResultData;
import ru.kontur.extern_api.sdk.model.SignInitiation;
import ru.kontur.extern_api.sdk.model.Signature;
import ru.kontur.extern_api.sdk.model.SortOrder;
import ru.kontur.extern_api.sdk.model.RecognizedMeta;

@JsonSerialization(GsonProvider.LIBAPI)
@ApiResponseConverter(LibapiResponseConverter.class)
public interface DocflowsApi {

    /**
     * Search docflows
     *
     * @param accountId target account
     * @param skip offset
     * @param take size
     * @param order sort order (sorting field -- date)
     * @param filters filters by {@link DocflowFilter}
     */
    @GET("v1/{accountId}/docflows")
    CompletableFuture<ApiResponse<DocflowPage>> search(
            @Path("accountId") UUID accountId,
            @Query("skip") long skip,
            @Query("take") int take,
            @Query("orderBy") SortOrder order,
            @QueryMap Map<String, String> filters
    );

    /**
     * Get Docflow object
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}")
    CompletableFuture<ApiResponse<Docflow>> get(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId
    );

    /**
     * Get all document from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents")
    CompletableFuture<ApiResponse<List<Document>>> getDocuments(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId
    );

    /**
     * Get discrete document from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}")
    CompletableFuture<ApiResponse<Document>> getDocument(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId
    );

    /**
     * Get discrete document description from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/description")
    CompletableFuture<ApiResponse<DocflowDocumentDescription>> getDocumentDescription(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId
    );

    /**
     * Get discrete encrypted document content from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/encrypted-content")
    CompletableFuture<ApiResponse<byte[]>> getEncryptedContent(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId
    );

    /**
     * Get discrete decrypted document content from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/decrypted-content")
    CompletableFuture<ApiResponse<byte[]>> getDecryptedContent(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId
    );

    /**
     * Get discrete document signatures from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/signatures")
    CompletableFuture<ApiResponse<List<Signature>>> getSignatures(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId
    );

    /**
     * Get discrete document single signature from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param signatureId Signature identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/signatures/{signatureId}")
    CompletableFuture<ApiResponse<Signature>> getSignature(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("signatureId") UUID signatureId
    );

    /**
     * Get discrete document signature single content from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param signatureId Signature identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/signatures/{signatureId}/content")
    CompletableFuture<ApiResponse<byte[]>> getSignatureContent(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("signatureId") UUID signatureId
    );

    /**
     * Get document print from docflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param request (required)
     */
    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/print")
    CompletableFuture<ApiResponse<byte[]>> print(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Body ByteContent request
    );

    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/recognize")
    CompletableFuture<ApiResponse<RecognizedMeta>> recognize(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Body @Raw byte[] content
    );

    /**
     * Get Reply document from specified workflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param documentType Reply document identifier (required)
     */
    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/generate-reply")
    CompletableFuture<ApiResponse<ReplyDocument>> generateReplyDocument(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Query("documentType") String documentType,
            @Body CertificateContent certificate
    );

    /**
     * Get Reply document from specified workflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param replyId Reply document identifier (required)
     */
    @GET("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/replies/{replyId}")
    CompletableFuture<ApiResponse<ReplyDocument>> getReplyDocument(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("replyId") UUID replyId
    );

    /**
     * Put content to Reply document from specified workflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param replyId Reply document identifier (required)
     * @param content (required)
     */
    @PUT("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/replies/{replyId}/content")
    CompletableFuture<ApiResponse<ReplyDocument>> updateReplyDocumentContent(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("replyId") UUID replyId,
            @Body @Raw byte[] content
    );

    @PUT("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/replies/{replyId}/signature")
    CompletableFuture<ApiResponse<ReplyDocument>> putReplyDocumentSignature(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("replyId") UUID replyId,
            @Body @Raw byte[] signature
    );

    /**
     * Put content to Reply document from specified workflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param replyId Reply document identifier (required)
     * @param data (required)
     */
    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/replies/{replyId}/send")
    CompletableFuture<ApiResponse<Docflow>> sendReply(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("replyId") UUID replyId,
            @Body SenderIp data
    );

    /**
     * Cloud sign Reply document from specified workflow
     *
     * @param accountId Account identifier (required)
     * @param docflowId Docflow object identifier (required)
     * @param documentId Document identifier (required)
     * @param replyId Reply document identifier (required)
     */
    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/replies/{replyId}/cloud-sign")
    CompletableFuture<ApiResponse<SignInitiation>> cloudSignReplyDocumentInit(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("replyId") UUID replyId,
            @Query("forceConfirmation") boolean forceConfirmation
    );

    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/replies/{replyId}/cloud-sign-confirm")
    CompletableFuture<ApiResponse<SignConfirmResultData>> cloudSignReplyDocumentConfirm(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Path("replyId") UUID replyId,
            @Query("requestId") String requestId,
            @Query("code") String code
    );

    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/decrypt-content")
    CompletableFuture<ApiResponse<DecryptInitiation>> cloudDecryptDocumentInit(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Body CertificateContent certificate
    );

    @POST("v1/{accountId}/docflows/{docflowId}/documents/{documentId}/decrypt-content-confirm")
    CompletableFuture<ApiResponse<byte[]>> cloudDecryptDocumentConfirm(
            @Path("accountId") UUID accountId,
            @Path("docflowId") UUID docflowId,
            @Path("documentId") UUID documentId,
            @Query("requestId") String requestId,
            @Query("code") String code,
            @Query("unzip") boolean unzip
    );

}
