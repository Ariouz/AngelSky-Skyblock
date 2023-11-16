package fr.angelsky.skyblock.managers.server.crates;

import java.util.Arrays;

public enum CrateKey {

    VOTE("vote_crate_key", false, 0, "Vote", 0),
    FROZEN("frozen_crate_key", true, 75, "Givrée", 11),
    VOLCANIC("volcanic_crate_key", true, 75, "Volcanique", 15),
    JADE("jade_crate_key", true, 250, "de Jade", 28),
    AMETHYST("amethyst_crate_key", true, 250, "d'Améthyste", 34),
    ANGELIC("angelic_crate_key", true, 500, "Angélique", 21),
    DEMONIAC("demoniac_crate_key", true, 500, "Démoniaque", 23);

    private final String keyId;
    private final boolean inStore;
    private final int tokenPrice;
    private final String display;
    private final int storeSlot;

    CrateKey(String keyId, boolean inStore, int tokenPrice, String display, int storeSlot){
        this.keyId = keyId;
        this.inStore = inStore;
        this.tokenPrice = tokenPrice;
        this.display = display;
        this.storeSlot = storeSlot;
    }

    public String getKeyId() {
        return keyId;
    }

    public boolean isInStore() {
        return inStore;
    }

    public int getTokenPrice() {
        return tokenPrice;
    }

    public String getDisplay() {
        return display;
    }

    public int getStoreSlot() {
        return storeSlot;
    }

    public static CrateKey getById(String id){
        return Arrays.stream(values()).filter(key -> key.getKeyId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }
}
