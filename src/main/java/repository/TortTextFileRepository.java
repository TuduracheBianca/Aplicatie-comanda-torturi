package repository;

import domain.Tort;

import java.io.IOException;

public class TortTextFileRepository extends TextFileRepository<Tort> {

    public TortTextFileRepository(String fileName) throws IOException {
        super(fileName);
    }

    @Override
    protected String toCvs(Tort entity) {
        return Tort.toText(entity);
    }

    @Override
    public Tort createEntityFromCsv(String csvLine) {
        return Tort.fromText(csvLine);
    }
}