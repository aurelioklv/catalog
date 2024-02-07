package com.aurelioklv.catalog.data.mapper

import com.aurelioklv.catalog.data.local.entities.BreedEntity
import com.aurelioklv.catalog.data.model.Breed
import com.aurelioklv.catalog.data.network.model.NetworkBreed

fun NetworkBreed.asEntity(): BreedEntity = BreedEntity(
    id = id,
    name = name,
    weightMetric = weight.metric,
    weightImperial = weight.imperial,
    cfaUrl = cfaUrl,
    vetstreetUrl = vetstreetUrl,
    vcahospitalsUrl = vcahospitalsUrl,
    temperament = temperament,
    origin = origin,
    countryCodes = countryCodes,
    countryCode = countryCode,
    description = description,
    lifeSpan = lifeSpan,
    indoor = indoor,
    lap = lap,
    altNames = altNames,
    adaptability = adaptability,
    affectionLevel = affectionLevel,
    childFriendly = childFriendly,
    catFriendly = catFriendly,
    dogFriendly = dogFriendly,
    energyLevel = energyLevel,
    grooming = grooming,
    healthIssues = healthIssues,
    intelligence = intelligence,
    sheddingLevel = sheddingLevel,
    socialNeeds = socialNeeds,
    strangerFriendly = strangerFriendly,
    vocalisation = vocalisation,
    bidability = bidability,
    experimental = experimental,
    hairless = hairless,
    natural = natural,
    rare = rare,
    rex = rex,
    suppressedTail = suppressedTail,
    shortLegs = shortLegs,
    wikipediaUrl = wikipediaUrl,
    hypoallergenic = hypoallergenic,
    referenceImageId = referenceImageId,
)

fun BreedEntity.asExternalModel(): Breed = Breed(
    id = id,
    name = name,
    weightImperial = weightImperial,
    weightMetric = weightMetric,
    cfaUrl = cfaUrl,
    vetstreetUrl = vetstreetUrl,
    vcahospitalsUrl = vcahospitalsUrl,
    temperament = temperament,
    origin = origin,
    countryCodes = countryCodes,
    countryCode = countryCode,
    description = description,
    lifeSpan = lifeSpan,
    indoor = indoor,
    lap = lap,
    altNames = altNames,
    adaptability = adaptability,
    affectionLevel = affectionLevel,
    childFriendly = childFriendly,
    catFriendly = catFriendly,
    dogFriendly = dogFriendly,
    energyLevel = energyLevel,
    grooming = grooming,
    healthIssues = healthIssues,
    intelligence = intelligence,
    sheddingLevel = sheddingLevel,
    socialNeeds = socialNeeds,
    strangerFriendly = strangerFriendly,
    vocalisation = vocalisation,
    bidability = bidability,
    experimental = experimental,
    hairless = hairless,
    natural = natural,
    rare = rare,
    rex = rex,
    suppressedTail = suppressedTail,
    shortLegs = shortLegs,
    wikipediaUrl = wikipediaUrl,
    hypoallergenic = hypoallergenic,
    referenceImageId = referenceImageId,
)