package cloud.deuterium.maxbet.character;

import cloud.deuterium.maxbet.character.dtos.request.CreateItemDto;
import cloud.deuterium.maxbet.character.dtos.response.ItemDto;
import cloud.deuterium.maxbet.character.entities.CharacterClass;
import cloud.deuterium.maxbet.character.entities.Item;
import cloud.deuterium.maxbet.character.entities.WoGCharacter;

/**
 * Created by Milan Stojkovic 05-Apr-2024
 */
public class TestUtils {

    public static final String VALID_GAME_MASTER_JWT = "eyJraWQiOiI0MWQxNjA4MC1kNjJkLTQ2ODUtYjIxNy04ZTBkNGQwYmVkYjQiLCJhbGciOiJSUzI1NiJ9.eyJhcHAiOiJXb3JsZCBvZiBHYW1lcyIsInN1YiI6Im1hc3RlckB3b2cuaW8iLCJhdWQiOiJ3b2dfcHVibGljX2NsaWVudCIsIm5iZiI6MTcxMjI3NTU1Mywic2NvcGUiOlsib3BlbmlkIl0sInJvbGVzIjpbIlJPTEVfR0FNRV9NQVNURVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4ODg4IiwiaWQiOiJmNzI1ZDcyZi0yMzBiLTRhODYtOTY4MS05ZDg3NWVlZDhjZGIiLCJleHAiOjI3MTIyNzU4NTMsImlhdCI6MTcxMjI3NTU1MywianRpIjoiZmM1ZmQ4NDQtNGE2Yy00ZTBmLTkzNjQtNGI1Yjc2MmIxYTI3In0.Av-c6f_w8trPVuw_MzAq3LOs6iLfXwLImDpLXHEU27pKR6zKON_BCIWlihPMNhg_DsyVKA6b6Hv05UvMMbt9IZ9wJQEBlQP87BnS8dAPhP9gDUO4Z6NqCTfjniVOFlImxThk5GPmFlpWdw-TcjMhChsa2og5Ond_5ZvStvSlMb-GP8JXT361yix2k5uHDRHji7s1JsboUuq5OsD0puZX9PJ4I16YZTW184xCjkt9KXBEogaDjU_ILSKxwtH1cog55yLWA5ETFZwZOf6neii0HxSYzTCy-ux8ZMS6rTseGlwlyM5eonFczbnaTpIW2-pPYYY2G8Civ1RPqE_7_i9nag";
    public static final String EXPIRED_GAME_MASTER_JWT = "eyJraWQiOiI0MWQxNjA4MC1kNjJkLTQ2ODUtYjIxNy04ZTBkNGQwYmVkYjQiLCJhbGciOiJSUzI1NiJ9.eyJhcHAiOiJXb3JsZCBvZiBHYW1lcyIsInN1YiI6Im1hc3RlckB3b2cuaW8iLCJhdWQiOiJ3b2dfcHVibGljX2NsaWVudCIsIm5iZiI6MTcxMjI3NTU1Mywic2NvcGUiOlsib3BlbmlkIl0sInJvbGVzIjpbIlJPTEVfR0FNRV9NQVNURVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4ODg4IiwiaWQiOiJmNzI1ZDcyZi0yMzBiLTRhODYtOTY4MS05ZDg3NWVlZDhjZGIiLCJleHAiOjE3MTIyNzU4NTMsImlhdCI6MTcxMjI3NTU1MywianRpIjoiZmM1ZmQ4NDQtNGE2Yy00ZTBmLTkzNjQtNGI1Yjc2MmIxYTI3In0.mAx-fLvDJVNDNW2WIHhkuHlq64w-xnMZAU-tiOIbkPSI0N7kR5FODLtRRCsFkK-ncGBmssr0cbjkeehq5dC3Ad-1YJEc4384fXLinlKcrwTJAA57jZHnRtww5MzxHFKbDu_YSvgEf0EiIigmGH24BWgf5HxV3a_KSOKbL3bEKkuSX_pnvESz8O8q_VLfbc6Q9D8es3zwe0nygaNitcJS8-HzzG7JAmbqBbpnT2PWpnaGjsNz4R_AVGRp_GwCz8MsF0D23VzKbSDwlZ7EpwDvU-YLwFeY3otM6mBk2-UmiJA6p2Hr3Vbv4TDc-5gBzcpfy26R0ev7sF3MKQCshz3x9A";
    public static final String VALID_USER_JWT = "eyJraWQiOiI0MWQxNjA4MC1kNjJkLTQ2ODUtYjIxNy04ZTBkNGQwYmVkYjQiLCJhbGciOiJSUzI1NiJ9.eyJhcHAiOiJXb3JsZCBvZiBHYW1lcyIsInN1YiI6InVzZXJAd29nLmlvIiwiYXVkIjoid29nX3B1YmxpY19jbGllbnQiLCJuYmYiOjE3MTIyNzU1NTMsInNjb3BlIjpbIm9wZW5pZCJdLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4ODg4IiwiaWQiOiIyNDI1NTEyZC1jY2M5LTRjMjItYjRkMS05MmI4NTJjODdjZjYiLCJleHAiOjI3MTIyNzU4NTMsImlhdCI6MTcxMjI3NTU1MywianRpIjoiZmM1ZmQ4NDQtNGE2Yy00ZTBmLTkzNjQtNGI1Yjc2MmIxYTI3In0.MDgdMTrYF7OHBMSA4OPzxGdl4RduHphPQJDPHykRDfvX98IJjFuXtPdyB9Qz6z5CHyzIfvdOJG0LYdFnP16Av7g96dLSkavbohMGBpK8pwfyV1WMPAXF9I9lZt87I9LSIg3VzXW6YaEZGtM7ppQY9pultjpVhMACBRS-CY-1qbLUVjwsHwEi-laNS7ibePQtmSQa9ie_SEdyETNzUCmP1yXatYWzvTiAEgCq5o6suHsR1OPieVqXjLCOXx4PlTy16zwnoTpDid8s6bxGC5UDIC6Cf--FtfPjYPFLebPuvkrEvXhEdQgweEc51ZHwVh0sQ671iH9wWKdrc-BUp6mTNw";
    public static final String EXPIRED_USER_JWT = "eyJraWQiOiI0MWQxNjA4MC1kNjJkLTQ2ODUtYjIxNy04ZTBkNGQwYmVkYjQiLCJhbGciOiJSUzI1NiJ9.eyJhcHAiOiJXb3JsZCBvZiBHYW1lcyIsInN1YiI6InVzZXJAd29nLmlvIiwiYXVkIjoid29nX3B1YmxpY19jbGllbnQiLCJuYmYiOjE3MTIyNzU1NTMsInNjb3BlIjpbIm9wZW5pZCJdLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4ODg4IiwiaWQiOiIyNDI1NTEyZC1jY2M5LTRjMjItYjRkMS05MmI4NTJjODdjZjYiLCJleHAiOjE3MTIyNzU4NTMsImlhdCI6MTcxMjI3NTU1MywianRpIjoiZmM1ZmQ4NDQtNGE2Yy00ZTBmLTkzNjQtNGI1Yjc2MmIxYTI3In0.gS6PqWa21nXVqfhmv6vEbW-iotkD1RCG5Z86RHiURxRaZbo2QSjGlBnkYQHXuFhKm2pnINTVKTVfZXFkmz5OhU5qzoJV9lUnV457xx6wVvfiUnZ5tI6x0l8y9qauXOLZEAV2QWAbUegF7V-raZ4r3OiSsJ_brBgeym6LC8DYv3QprcTIjudAvOrSh7PNNF-rRlxzo8jLToUNmg90_Kq8-477X4pVqfNUXD3GqE5aHmRE_63JddUoXI-O1vIGVT0Kx6BSVfh2dV99LdROjCzqsU3QuezvunG37aXT7C0F7Ao3QQeNsqB2epBDvsFr2ZgaV3cQ4calgBecfV2knvyp_Q";
    public static final String VALID_USER_2_JWT = "eyJraWQiOiI0MWQxNjA4MC1kNjJkLTQ2ODUtYjIxNy04ZTBkNGQwYmVkYjQiLCJhbGciOiJSUzI1NiJ9.eyJhcHAiOiJXb3JsZCBvZiBHYW1lcyIsInN1YiI6InVzZXIyQHdvZy5pbyIsImF1ZCI6IndvZ19wdWJsaWNfY2xpZW50IiwibmJmIjoxNzEyMjc1NTUzLCJzY29wZSI6WyJvcGVuaWQiXSwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODg4OCIsImlkIjoiMTYxZTkyNzMtOTk2YS00MTFlLWI4OGUtZmI2MDhiOThiNmE3IiwiZXhwIjoyNzEyMjc1ODUzLCJpYXQiOjE3MTIyNzU1NTMsImp0aSI6ImZjNWZkODQ0LTRhNmMtNGUwZi05MzY0LTRiNWI3NjJiMWEyNyJ9.ON1y9qRKiyGIVTTlMmFS1-7xqyFaQ48pzi8kEUZ6vpIot3kE0J0gV1TL8hHghFFrk6d82eiRX8ZivyPiLox9hVnsnfP54QBSQ2jdL85s2yEnze9msv3uN1V76OjBqbg_tIWnruprzWDIe-iQdYztANySgee37edUKLKMZ39V45QkOxInAvu_pZuma2fQk_-bI1XKs-xoJjAA37-Gz8ymBJ9N7zbUcEz83GHWWTwsQQ_MLVrg8eaHgN3UczlCyF0q0jJJPxTgPcAhb5OuRH-oB_yRAYhqIEP7FeW_k64vM2-bvOsaHZsmV4RCagixOUB92X80gjCljeQLRsjbcY4s7Q";


    public static ItemDto createItemDto() {
        return new ItemDto(
                "8d1134c7-57e8-43ec-965d-abb7efd31047",
                "Sword",
                "A sword is an edged, bladed weapon intended for manual cutting or thrusting.",
                50,
                0,
                10,
                0
        );
    }

    public static CreateItemDto getCreateItemDto() {
        return new CreateItemDto(
                "Sword",
                "A sword is an edged, bladed weapon intended for manual cutting or thrusting.",
                50,
                0,
                10,
                0
        );
    }

    public static CharacterClass createWarriorClass() {
        return new CharacterClass(
                "6ded175b-24d8-4ad3-b068-5ee6a0bf9398",
                "Warrior",
                "Warrior Description"
        );
    }

    public static WoGCharacter createCharacter() {
        return new WoGCharacter(
                "1a70816c-1939-4417-97b1-fe349dd7442a",
                "Marcus",
                100,
                100,
                80,
                40,
                10,
                20,
                "2425512d-ccc9-4c22-b4d1-92b852c87cf6",
                null,
                null
        );
    }

    public static Item createItem() {
        return new Item(
                "8d1134c7-57e8-43ec-965d-abb7efd31047",
                "Sword",
                "A sword is an edged, bladed weapon intended for manual cutting or thrusting.",
                50,
                0,
                10,
                0
        );
    }
}
