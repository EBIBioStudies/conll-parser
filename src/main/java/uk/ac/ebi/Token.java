package uk.ac.ebi;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A class representing a single token in CoNLL-U format.
 */
public class Token {
    String id;
    String form;
    String lemma;
    UPOS tag;
    String xpos;
    String feats;
    String head;
    String deprel;
    String deps;
    String misc;

    public Token(String[] fields) {
        this.id = fields[0];
        this.form = fields[1];
        this.lemma = fields[2];
        this.tag = UPOS.valueOf(fields[3]);
        this.xpos = fields[4];
        this.feats = fields[5];
        this.head = fields[6];
        this.deprel = fields[7];
        this.deps = fields[8];
        this.misc = fields[9];
    }

    @Override
    public String toString() {
        return String.format("ID: %s, Form: %s, Lemma: %s, UPOS: %s, XPOS: %s, Feats: %s, Head: %s, Deprel: %s, Deps: %s, Misc: %s",
                id, form, lemma, tag, xpos, feats, head, deprel, deps, misc);
    }

    public enum UPOS {
        ADJ("adjective"),
        ADP("adposition"),
        ADV("adverb"),
        AUX("auxiliary"),
        CCONJ("coordinating conjunction"),
        DET("determiner"),
        INTJ("interjection"),
        NOUN("noun"),
        NUM("numeral"),
        PART("particle"),
        PRON("pronoun"),
        PROPN("proper noun"),
        PUNCT("punctuation"),
        SCONJ("subordinating conjunction"),
        SYM("symbol"),
        VERB("verb"),
        X("other");
    
        private final String description;
    
        UPOS(String description) {
            this.description = description;
        }
    
        public String getDescription() {
            return description;
        }
    }
}
