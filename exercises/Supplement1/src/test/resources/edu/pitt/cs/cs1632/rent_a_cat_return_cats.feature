Feature: Rent-A-Cat returning

As a user
I want to return a cat that I rented back to the rent-a-cat facility
So that I can be not accused of cat abduction.

Background:
# TODO: Include your test fixture here
Given a rent-a-cat facility
And a cat with ID 1 and name "Jennyanydots"
And a cat with ID 2 and name "Old Deuteronomy"
And a cat with ID 3 and name "Mistoffelees"


Rule: When a cat is requested for return and the cat has not been rented,
the return is unsuccessful and the list of available cats remains the same.

Scenario: Attempt to return a cat that does not exist
When I return cat number 4
Then the return is unsuccessful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"

Scenario: Rent a cat and attempt to return the same cat twice
# TODO: complete this 

When I rent cat number 1
Then the rent is successful
When I list the cats
Then the listing is: "ID 2. Old Deuteronomy\nID 3. Mistoffelees\n"
When I return cat number 1
Then the return is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"
When I return cat number 1
Then the return is unsuccessful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"


Rule: When a cat is requested for return and the cat has been rented,
the return is successful and the cat is added to the list of available cats.

Scenario: Rent the first cat and then return the first cat
# TODO: complete this scenario

When I rent cat number 1
Then the rent is successful
When I list the cats
Then the listing is: "ID 2. Old Deuteronomy\nID 3. Mistoffelees\n"
When I return cat number 1
Then the return is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"


Scenario: Rent the last cat and then return the last cat
# TODO: complete this scenario
When I rent cat number 3
Then the rent is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\n"
When I return cat number 3
Then the return is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"


Scenario: Rent a middling cat and then return that cat
# TODO: complete this scenario
When I rent cat number 2
Then the rent is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 3. Mistoffelees\n"
When I return cat number 2
Then the return is successful
When I list the cats
Then the listing is: "ID 1. Jennyanydots\nID 2. Old Deuteronomy\nID 3. Mistoffelees\n"
