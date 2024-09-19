
# CoNLL-U Parser

 - The CoNLL-U format is a way to organize and analyze sentences in a structured table.
 - Each row in the table represents a word in the sentence.
 - Each columns provide information about a word, such as its part of speech (like noun or verb), its grammatical role, and how it connects to other words in the sentence.
 - Lines starting with # are comments

| **Column** | **Description**                                                                                      |
|------------|------------------------------------------------------------------------------------------------------|
| **ID**     | The position of the word in the sentence, starting from 1. It helps identify the order of words.    |
| **FORM**   | The actual word as it appears in the text. For example, "cat" or "running."                        |
| **LEMMA**  | The base or dictionary form of the word. For instance, the lemma of "running" is "run."            |
| **UPOS**   | The universal part-of-speech tag that indicates the grammatical category of the word, such as NOUN (noun), VERB (verb), or ADJ (adjective). |
| **XPOS**   | The specific part-of-speech tag used in the original text or corpus, which might be more detailed or language-specific. |
| **FEATS**  | Additional grammatical features or attributes of the word, such as tense, number, or case. This provides extra details about the word’s role in the sentence and are delimited by the pipe sign `\|`.|
| **HEAD**   | The ID of the word that is the syntactic head of the current word. For example, in the phrase "the cat sleeps," "sleeps" would be the head of "cat." |
| **DEPREL** | The type of grammatical relationship between the word and its head. For example, "det" for determiner or "nsubj" for nominal subject. |
| **DEPS**   | (Optional) A list of additional dependencies, if there are multiple relations or connections.        |
| **MISC**   | (Optional) Any other miscellaneous information or notes about the word.                             |


Example:

```
# sent_id = 1
# text = The cat sleeps
1       The     the     DET     DT      Definite=Def|PronType=Art       2       det     _       _
2       cat     cat     NOUN    NN      Number=Sing     3       nsubj   _       _
3       sleeps  sleep   VERB    VBZ     Number=Sing|Person=3|Tense=Pres|VerbForm=Fin    0       ROOT    _       SpaceAfter=No
```

Dependency graph viewer: https://urd2.let.rug.nl/~kleiweg/conllu/
