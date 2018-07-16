module CatList (CatList) where
    import Prelude hiding (head, tail, (++))
    import CatenableList
    import Queue

    data CatList queue value = E | C value (queue (CatList queue value))
    -- Uses the snoc operation of the queue
    link (C value queue) s = C value (Queue.snoc queue s)
    
    linkAll queue = let queueHead = (Queue.head queue)
                        queueTail = (Queue.tail queue)
                    in  if (Queue.isEmpty queueTail) 
                        then queueHead 
                        else (link queueHead (linkAll queueTail)) 
    -- The Catenable List takes a Queue as an argument
    instance Queue queue => CatenableList (CatList queue) where
        empty = E
        isEmpty E = True
        isEmpty _ = False
    
        cat ++ E = cat
        E ++ cat = cat
        cat1 ++ cat2 = link cat1 cat2
        -- cons and snoc simply create a new CatList and use (++)
        cons value cat = (C value Queue.empty) ++ cat
        snoc cat value = cat ++ (C value Queue.empty)
    
        head E = error "empty list"
        head (C value queue) = value
    
        tail E = error "empty list"
        tail (C value queue) =  if (Queue.isEmpty queue) 
                                then E 
                                else linkAll queue
