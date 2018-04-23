/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern.service;

import java.util.concurrent.CompletableFuture;
import ru.skbkontur.sdk.extern.model.CertificateList;
import ru.skbkontur.sdk.extern.service.transport.adaptors.QueryContext;

/**
 *
 * @author alexs
 */
@SuppressWarnings("unused")
public interface CertificateService {
	CompletableFuture<QueryContext<CertificateList>> getCertificateListAsync();
	QueryContext<CertificateList> getCertificateList(QueryContext<?> cxt);
}
