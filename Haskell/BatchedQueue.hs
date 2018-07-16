module BatchedQueue (BatchedQueue) where
    import Prelude hiding (head, tail)
    import Queue

    data BatchedQueue value = BQ [value] [value]

    -- check not used instead we use pattern matching
    

    instance Queue BatchedQueue where
        empty = BQ [] []
        -- if the front is empty the rear must also be empty
        isEmpty (BQ front _) = null front
        -- if the front is empty we insert at the front otherwise at the rear
        snoc (BQ [] rear) x = BQ (x : front) rear
        snoc (BQ front rear) x = BQ front (x : rear)

        head (BQ [] _) = error "empty queue"
        head (BQ (value : front) rear) = value
        -- if the front only contains one element we remove it 
        -- and also reverse the rear and put it at the front
        tail (BQ [] _) = error "empty queue"
        tail (BQ (value : []) rear) = BQ (reverse rear) []
        tail (BQ (value : front) rear) = BQ front rear
