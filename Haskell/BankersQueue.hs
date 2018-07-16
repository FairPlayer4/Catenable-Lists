module BankersQueue (BankersQueue) where
    import Prelude hiding (head, tail)
    import Queue

    data BankersQueue value = BQ Int [value] Int [value]

    -- lenf/lenr is the length of the front/rear list
    -- check makes sure that the rear is added to the front if it is larger than the front
    check lenf front lenr rear =
        if lenr <= lenf 
        then BQ lenf front lenr rear
        else BQ (lenf + lenr) (front ++ reverse rear) 0 []

    instance Queue BankersQueue where
        empty = BQ 0 [] 0 []
        isEmpty (BQ lenf _ _ _) = lenf == 0
        -- the value is prepended to the rear and the result is checked
        snoc (BQ lenf front lenr rear) value = check lenf front (lenr + 1) (value : rear)

        head (BQ _ [] _ _) = error "empty queue"
        head (BQ _ (value : front) _ _)  = value
        
        tail (BQ _ [] _ _) = error "empty queue"
        tail (BQ lenf (value : front) lenr rear) = check (lenf - 1) front lenr rear
