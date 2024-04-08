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



INSERT INTO character(id, name, health, mana, base_strength, base_agility, base_faith, base_intelligence,
                      character_class_id, created_by)
VALUES ('1a70816c-1939-4417-97b1-fe349dd7442a', 'Marcus', 100, 100, 80, 40, 10, 20,
        '6ded175b-24d8-4ad3-b068-5ee6a0bf9398', '2425512d-ccc9-4c22-b4d1-92b852c87cf6');
