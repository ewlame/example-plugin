package com.wikiblob;

import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.CommandExecuted;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@PluginDescriptor(
	name = "Wiki Blob"
)
public class WikiBlobPlugin extends Plugin
{
	private static final Set<Integer> QUESTS_VARBIT = ImmutableSet.of(2561, 2378, 3468, 6071, 3185, 299, 3274, 487, 2573, 2258, 358, 1465, 2780, 2639, 1560, 2866, 2497, 1803, 2326, 334, 822, 961, 217, 571, 346, 1527, 34, 418, 1990, 532, 2448, 1383, 260, 1103, 2790, 1404, 1850, 657, 2140, 2610, 1372, 2011, 1444, 2098, 1028, 451, 1051, 3293, 3311, 3337, 3523, 3534, 3550, 3618, 2783, 3888, 5027, 5619, 5795, 6037, 6027, 6104, 6358, 6396, 6528, 7796, 7856, 4982, 5078, 3330, 8403, 1344, 3290, 5347, 6067, 10570, 8063, 9016, 9459, 7255, 10582, 693, 12063, 12276, 12296, 821, 1391);
	private static final Set<Integer> QUESTS_VARP = ImmutableSet.of(130, 29, 31, 176, 32, 160, 122, 71, 273, 107, 144, 63, 179, 178, 67, 293, 68, 655, 10, 399, 314, 131, 80, 0, 335, 148, 17, 11, 347, 65, 180, 150, 382, 223, 188, 5, 387, 175, 139, 147, 14, 365, 30, 517, 192, 307, 112, 416, 165, 302, 714, 328, 402, 600, 76, 159, 339, 60, 116, 320, 26, 359, 197, 226, 111, 200, 385, 317, 161, 212, 980, 714, 492, 77, 267, 145, 299);
//	private static final Set<Integer> ACHIEVEMENT_VARP = ImmutableSet.of(130, 29);

	@Inject
	private Client client;

	@Subscribe
	public void onCommandExecuted(CommandExecuted event) {
		if (event.getCommand().equalsIgnoreCase("blob")) {
			Gson gson = new Gson();
			Map<Integer, Integer> questVarbit = VarbitMapper(QUESTS_VARBIT);
			Map<Integer, Integer> questVarp = VarpMapper(QUESTS_VARP);
//			Map<Integer, Integer> achieveVarp = VarpMapper(ACHIEVEMENT_VARPS);

			String questVarbitJSON = gson.toJson(questVarbit);
			String questVarpJSON = gson.toJson(questVarp);
//			String achieveVarpJSON = gson.toJson(achieveVarp);
			String blob = "{\"Quest\":[{\n\"varbit\":" + questVarbitJSON + ",\n\"varp\":" + questVarpJSON + "}]}";

			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(blob), null);
		}
	}

	public Map<Integer, Integer> VarpMapper(Set<Integer> mySet) {
		Map<Integer, Integer> myHashMap = new HashMap<>();
		for (int e : mySet) {
			myHashMap.put(e, client.getVarpValue(e));
		}
		return myHashMap;
	}

	public Map<Integer, Integer> VarbitMapper(Set<Integer> mySet) {
		Map<Integer, Integer> myHashMap = new HashMap<>();
		for (int e : mySet) {
			myHashMap.put(e, client.getVarbitValue(e));
		}
		return myHashMap;
	}
}
