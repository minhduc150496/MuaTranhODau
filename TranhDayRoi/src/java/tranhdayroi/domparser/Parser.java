/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tranhdayroi.domparser;

import java.util.List;
import tranhdayroi.jaxb.Painting;

/**
 *
 * @author MinhDuc
 */
public interface Parser {
    public int getnSuccess();
    public int getnFail();
    public List<Painting> getResults();
    public void parse();
    public void parseFromDomain(Parser p);
}
