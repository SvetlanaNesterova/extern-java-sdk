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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kontur.extern_api.sdk.model;


import ru.kontur.extern_api.sdk.model.ClientInfo.Recipient;

import java.util.Optional;

public class TestData {

    private ClientInfo clientInfo;
    private String[] docs;

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String[] getDocs() {
        return docs;
    }

    public void setDocs(String[] docs) {
        this.docs = docs;
    }

    @Override
    public String toString() {
        try {
            Recipient recipient = clientInfo.getRecipient();
            String sRecipient = Optional.ofNullable(recipient.getIfnsCode())
                    .map(ifns -> "ifns-code " + ifns)
                    .orElseGet(() -> "togs-code " + recipient.getTogsCode());

            return "TestData{"
                    + " sender=" + clientInfo.getSender().getFio()
                    + " recipient=" + sRecipient
                    + " }";

        } catch (Exception ignored) {
            return super.toString();
        }


    }
}
