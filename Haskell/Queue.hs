module Queue (Queue(..)) where
    import Prelude hiding (head,tail)

-- The value can be any type for example string or integer
class Queue queue where
    -- Represents the empty queue
    empty   :: queue value
    -- Takes a queue as an argument and returns whether it is empty
    isEmpty :: queue value -> Bool
    -- Takes a queue and a value as arguments 
    -- Returns a queue where the value was added at the end
    snoc    :: queue value -> value -> queue value
    -- Takes a queue as an argument and returns the first element of the queue
    head    :: queue value -> value
    -- Takes a queue as an argument and returns the queue without the first element
    tail    :: queue value -> queue value
        