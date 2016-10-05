package com.bnade.wow.client;

import com.bnade.wow.client.model.Item;

public interface WowAPI {

	Item getItem(int id) throws WowClientException;
}
