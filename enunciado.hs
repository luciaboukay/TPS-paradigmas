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
module City (City, newC, nameC, distanceC) where

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
linksL (Cit Name1 Point1) (Cit Name2 Point2) Lin (Cit Name3 Point1) (Cit Name4 Point2) (Qua Material Capacity Last) = if (connectsL (Cit Name1 Point1) Lin (Cit Name3 Point1) (Cit Name4 Point2) (Qua Material Capacity Last)) &&  connectsL (Cit Name2 Point2) Lin (Cit Name3 Point1) (Cit Name4 Point2) (Qua Material Capacity Last) then True
                                                                                                                       else False
capacityL :: Link -> Int
capacityL Lin (Cit Name3 Point1) (Cit Name4 Point2) (Qua Material Capacity Last) = round(delayQ Qua)
delayL :: Link -> Float     -- la demora que sufre una conexion en este canal
delayL Lin (Cit Name3 Point1) (Cit Name4 Point2) (Qua Material Capacity Last) = (delayQ Qua) * distance (Cit Name3 Point1) (Cit Name4 Point2)
-------------------
module Tunel ( Tunel, newT, connectsT, usesT, delayT )
   where

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
newT [Lin (Cit Name1 Point1) (Cit Name2 Point2) (Qua Material Capacity Last)] = Tun [Lin (Cit Name1 Point1) (Cit Name2 Point2) (Qua Material Capacity Last)]
connectsT :: City -> City -> Tunel -> Bool -- inidca si este tunel conceta estas dos ciudades distintas
connectsT ((Cit Name Point) (Cit Name1 Point1) (Tun [Lin (Cit Name2 Point2) (Cit Name3 Point3) (Qua Material Capacity Last)])) = if linksL ((Cit Name Point) (Cit Name1 Point1) (Lin (Cit Name2 Point2) (Cit Name3 Point3) (Qua Material Capacity Last))) then True
                                                                                                                                   else False
usesT :: Link -> Tunel -> Bool  -- indica si este tunel atraviesa ese link
usesT (Lin (Cit Name1 Point1) (Cit Name2 Point2) (Qua Material Capacity Last)) (Tun [Lin (Cit Name3 Point3) (Cit Name4 Point4) (Qua Material Capacity Last)]) = 
delayT :: Tunel -> Float -- la demora que sufre una conexion en este tunel
append_delays :: [a] -> [a]
append_delays Tun links = foldl (\fold each -> delayL(each): fold) links
delayT Tun Links_1 = foldr (+) 0 (append_delays(Links_1)) 
-------------------
module Region ( Region, newR, foundR, linkR, tunelR, pathR, linksForR, connectedR, linkedR, delayR, availableCapacityForR, usedCapacityForR )
   where

data Region = Reg [City] [Link] [Tunel]
newR :: Region
newR = Reg [City] [Link] [Tunel]
foundR :: Region -> City -> Region -- agrega una nueva ciudad a la región
foundR (Reg [City] [Link] [Tunel]) city = city ++ [City]
linkR :: Region -> City -> City -> Quality -> Region -- enlaza dos ciudades de la región con un enlace de la calidad indicada
tunelR :: Region -> [ City ] -> Region -- genera una comunicación entre dos ciudades distintas de la región
connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
linkedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan enlazadas
delayR :: Region -> City -> City -> Float -- dadas dos ciudades conectadas, indica la demora
availableCapacityForR :: Region -> City -> City -> Int -- indica la capacidad disponible entre dos ciudades
