package com.bnade.wow.client;

import com.bnade.wow.client.model.Item;

public interface WoWAPI {

	Item getItem(int id) throws WoWClientException;
}
