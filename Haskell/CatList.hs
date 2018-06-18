module CatList (CatList) where
    import Prelude hiding (head,tail,(++))
    import CatenableList
    import Queue (Queue)
    import qualified Queue

    data CatList queue value = E | C value (queue (CatList queue value))

    link (C x queue) s = C x (Queue.snoc queue s)

    instance Queue queue => CatenableList (CatList queue) where
        empty = E
        isEmpty E = True
        isEmpty _ = False

        xs ++ E = xs
        E ++ xs = xs
        xs ++ ys = link xs ys

        cons x xs = C x Queue.empty ++ xs
        snoc xs x = xs ++ C x Queue.empty

        head E = error "empty list"
        head (C x queue) = x

        tail E = error "empty list"
        tail (C x queue) = if (Queue.isEmpty queue) then E else linkAll queue 
            where linkAll queue = if (Queue.isEmpty queueTail) then queueHead else (link queueHead (linkAll queueTail)) 
                    where   queueHead = (Queue.head queue) 
                            queueTail = (Queue.tail queue)
