package ru.kontur.extern_api.sdk.model;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.kontur.extern_api.sdk.model.ion.*;
import ru.kontur.extern_api.sdk.model.ion.ClientInfo;

public class Ion5RequestContract extends IonRequestContract<Ion5RequestData> {
    public Ion5RequestContract(ClientInfo additionalOrgInfo, IonPeriod period, Ion5RequestData data) {
        super(additionalOrgInfo, period, data);
    }
}
