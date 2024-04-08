INSERT INTO character_class(id, name, description)
VALUES ('6ded175b-24d8-4ad3-b068-5ee6a0bf9398', 'Warrior',
        'Warrior character''s skills are centered around strength and physical combat with a wide variety of melee weapons, and the ability to survive while tanking for the group, usually at the cost of agility or range.');
INSERT INTO character_class(id, name, description)
VALUES ('cee45ee0-0bd9-45a3-befd-392da2363d14', 'Rogue',
        'A rogue is a versatile character, capable of sneaky combat and nimble tricks. The rogue is stealthy and dexterous, capable of finding and disarming traps and picking locks');
INSERT INTO character_class(id, name, description)
VALUES ('dacb4ff0-8d9d-4b8c-a2f5-68ade2341cb0', 'Mage',
        'Mages, by nature, alter the very fabric of reality. They don''t just call down fireballs on anything marked "target." Instead, they alter perceptions of truth and the possible around the world, as well as controlling probability, coincidence, and the forces that make up the universe');
INSERT INTO character_class(id, name, description)
VALUES ('4443ac7f-8b7a-4474-bdac-1754926dc06d', 'Priest',
        'The Priest is focused on healing and supportive spells, but can have some offensive capabilities, specially against Demons or the Undead');



INSERT INTO item(id, name, description, bonus_strength, bonus_agility, bonus_intelligence, bonus_faith)
VALUES ('b61c73fa-1bdc-4003-9464-76d890691823', 'Warhammer', 'Ghal Maraz, warhammer, which means "Skull-Splitter". Ghal Maraz is one of the greatest weapons ever made', 40, 5, 5, 10);

INSERT INTO item(id, name, description, bonus_strength, bonus_agility, bonus_intelligence, bonus_faith)
VALUES ('0a905c99-a504-4472-94bd-1724aca57a57', 'Sword', 'Dragon Tooth, One of twelve magical swords belonging to the Empire s Elector Counts, Dragon Tooth has long defended the Imperial heartlands and capital.', 30, 0, 10, 0);

INSERT INTO item(id, name, description, bonus_strength, bonus_agility, bonus_intelligence, bonus_faith)
VALUES ('3522c288-8345-46cb-9869-0006208b4a5f', 'Agility potion', 'Health potion', 0, 10, 0, 0);

INSERT INTO item(id, name, description, bonus_strength, bonus_agility, bonus_intelligence, bonus_faith)
VALUES ('a0fcffb8-8388-487c-a566-430a7ad445e4', 'Robe', 'Made of a heavy, natural cotton, these flowing robes bespeak wisdom beyond the ages.', 10, 0, 30, 30);




INSERT INTO character(id, name, health, mana, base_strength, base_agility, base_faith, base_intelligence,
                             character_class_id, created_by)
VALUES ('995658ff-144b-4f19-979f-811aaa063641', 'Karl Franz', 300, 100, 100, 100, 100, 100,
        '6ded175b-24d8-4ad3-b068-5ee6a0bf9398', 'c4411396-d157-477a-a22f-3eb31c520bd0');

INSERT INTO character(id, name, health, mana, base_strength, base_agility, base_faith, base_intelligence,
                             character_class_id, created_by)
VALUES ('d6608669-4332-4575-b8b1-5e3922ef4f12', 'Bright Wizard', 300, 200, 50, 30, 70, 100,
        'dacb4ff0-8d9d-4b8c-a2f5-68ade2341cb0', '2dd47d43-b747-4e7c-a18d-685c1474ccda');


INSERT INTO character_item (id, character_id, item_id)
VALUES ('8bef23eb-98fd-4559-8c6a-421453ffe19b', '995658ff-144b-4f19-979f-811aaa063641', 'b61c73fa-1bdc-4003-9464-76d890691823');

INSERT INTO character_item (id, character_id, item_id)
VALUES ('cc1ce2e1-7fc4-4ce9-af91-4059507e49b6', '995658ff-144b-4f19-979f-811aaa063641', '0a905c99-a504-4472-94bd-1724aca57a57');

INSERT INTO character_item (id, character_id, item_id)
VALUES ('6679b579-1dd5-418f-8c38-4c75be64750c', '995658ff-144b-4f19-979f-811aaa063641', '3522c288-8345-46cb-9869-0006208b4a5f');

INSERT INTO character_item (id, character_id, item_id)
VALUES ('d16ad1af-d0d8-469c-bdaa-ad0c1a7be639', 'd6608669-4332-4575-b8b1-5e3922ef4f12', '3522c288-8345-46cb-9869-0006208b4a5f');

INSERT INTO character_item (id, character_id, item_id)
VALUES ('ddc5bdf7-4acf-4831-935b-3daec162074f', 'd6608669-4332-4575-b8b1-5e3922ef4f12', 'a0fcffb8-8388-487c-a566-430a7ad445e4');
