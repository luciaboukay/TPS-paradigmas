--Telco

--Es una compañia que se dedica a comunicar las ciudades que se susbcriben a su servicio.
--Primero ingrese el mapa de la region.(*)
--Luego establece vínculos entre ellas de cierta calidad y capacidad.
--Finalmente establece canales que conectan distintas ciudades ocupando una unidad de 
--capacidad por cada enlace recorrido.

--Para sostener este modelo se cuenta con las siguientes entidades:

module Point ( Point, newP, difP)
   where

data Point = Poi Int Int deriving (Eq, Show)

newP :: Int -> Int -> Point
newP x y = Poi (x y)

difP :: Point -> Point -> Float  -- distancia absoluta
difP (Poi x1 y1) (Poi x2 y2) = sqrt(fromIntegral((x1-x2)^2+(y1-y2)^2))

-----------------
module City ( City, newC, nameC, distanceC )
   where

data City = Cit String Point deriving (Eq, Show)

newC :: String -> Point -> City
newC Name Point = Cit Name Point

nameC :: City -> String
nameC (Cit Name Point) = Name

distanceC :: City -> City -> Float
distance (Cit Name1 Point1) (Cit Name2 Point2) = difP (Point1 Point2)
-----------------
module Quality ( Quality, newQ, capacityQ, delayQ )
   where

data Quality = Qua String Int Float deriving (Eq, Show)

newQ :: String -> Int -> Float -> Quality
newQ Material Capacity Last = Qua Material Capacity Last

capacityQ :: Quality -> Int -- cuantos túneles puede tolerar esta conexión
capacityQ Qua Material Capacity Last = Capacity 

delayQ :: Quality -> Float  -- la demora por unidad de distancia que sucede en las conexiones de este canal
delayQ Qua Material Capacity Last = Last
-------------------
module Link ( Link, newL, linksL, connectsL, capacityL, delayL )
   where

data Link = Lin City City Quality deriving (Eq, Show)

newL :: City -> City -> Quality -> Link -- genera un link entre dos ciudades distintas
newL (Cit Name1 Point1) (Cit Name2 Point2) (Qua Material Capacity Last) = Lin (Cit Name1 Point1) (Cit Name2 Point2) (Qua Material Capacity Last)
connectsL :: City -> Link -> Bool   -- indica si esta ciudad es parte de este link
connectsL (Cit Name Point) (Lin (Cit Name1 Point1) (Cit Name2 Point2) (Qua Material Capacity Last)) = if ((Name == Name1) && (Point == Point1)) || ((Name == Name2) && (Point == Point2)) then True
                                                                                                        else False
linksL :: City -> City -> Link -> Bool -- indica si estas dos ciudades distintas estan conectadas mediante este link

capacityL :: Link -> Int
delayL :: Link -> Float     -- la demora que sufre una conexion en este canal
-------------------
module Tunel ( Tunel, newT, connectsT, usesT, delayT )
   where

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel
-------------------
module Region ( Region, newR, foundR, linkR, tunelR, pathR, linksForR, connectedR, linkedR, delayR, availableCapacityForR, usedCapacityForR )
   where

data Region = Reg [City] [Link] [Tunel]
newR :: Region
foundR :: Region -> City -> Region -- agrega una nueva ciudad a la región
linkR :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
tunelR :: Region -> [ City ] -> Region -- genera una comunicación entre dos ciudades distintas de la región
connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
linkedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
delayR :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
availableCapacityForR :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
