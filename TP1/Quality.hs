module Quality ( Quality, newQ, capacityQ, delayQ )
   where

data Quality = Qua String Int Float deriving (Eq, Show)

newQ :: String -> Int -> Float -> Quality
newQ material capacity delay | capacity < 0 = error "La calidad esta definida incorrectamente. La capacidad no puede ser negativa."
                             | delay == 0 = error "El delay no puede ser cero."
                             | otherwise = Qua material capacity (abs delay)

capacityQ :: Quality -> Int -- cuantos túneles puede tolerar esta conexión
capacityQ (Qua _ capacityQ _) = capacityQ

delayQ :: Quality -> Float  -- la demora por unidad de distancia que sucede en las conexiones de este canal
delayQ (Qua _ _ delay) = delay