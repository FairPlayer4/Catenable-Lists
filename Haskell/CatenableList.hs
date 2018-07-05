module CatenableList (CatenableList(..)) where
    import Prelude hiding (head,tail,(++))

    class CatenableList catenableList where
        empty :: catenableList value
        isEmpty :: catenableList value -> Bool

        cons :: value -> catenableList value -> catenableList value
        snoc :: catenableList value -> value -> catenableList value
        (++) :: catenableList value -> catenableList value -> catenableList value

        head :: catenableList value -> value
        tail :: catenableList value -> catenableList value