/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.skbkontur.sdk.extern.providers;

import ru.skbkontur.sdk.extern.service.SDKException;

/**
 *
 * @author AlexS
 */
public interface CryptoProvider {
	byte[] sign(String thumbprint, byte[] content) throws SDKException;
	byte[] getSignerCertificate(String thumbprint) throws SDKException;
}
