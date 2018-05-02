package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public final class OefeningFrameController extends GridPane {
    
    NavigatieController nav;
    OefeningOverzichtPanelController oefOverview;
    OefeningDetailPanelController oefDetail;
    OefeningGroepsbDetailPanelController oefDetailGroepsb;
    OefeningDoelstDetailPanelController oefDetailDoelst;
    
    public OefeningFrameController(DomeinController dc) {
        nav = new NavigatieController(dc);
        oefOverview = new OefeningOverzichtPanelController(dc.getOc(), this); // het scherm met de tabelview
        oefDetail = new OefeningDetailPanelController(dc.getOc(), this);
        oefDetailGroepsb = new OefeningGroepsbDetailPanelController(dc.getOc(), this);
        oefDetailDoelst = new OefeningDoelstDetailPanelController(dc.getOc(), this);
        
        oefOverview.addOefeningObserver(oefDetail); // het scherm met de datails van oefen
        oefOverview.addOefeningObserver(oefDetailGroepsb); // het scherm met lisviews gbw
        oefOverview.addOefeningObserver(oefDetailDoelst); // het scherm met lisviews doels
        oefDetailGroepsb.addOefeningObserver(oefDetail);
        oefDetailDoelst.addOefeningObserver(oefDetail);
        
        add(nav, 0, 0);
        setColumnSpan(nav, 2);
        add(oefOverview, 0, 1);
        add(oefDetail, 1, 1);
        add(oefDetailGroepsb, 0, 1);
        add(oefDetailDoelst, 0, 1);
        toonListview("cancel/init");
    }
    
    public void toonListview(String keuze) {
        switch (keuze.toLowerCase()) {
            case "gbw":
                oefOverview.setVisible(false);
                oefDetailDoelst.setVisible(false);
                oefDetailGroepsb.setVisible(true);
                break;
            case "doels":
                oefOverview.setVisible(false);
                oefDetailGroepsb.setVisible(false);
                oefDetailDoelst.setVisible(true);
                break;
            case "cancel/init":
                oefDetailGroepsb.setVisible(false);
                oefDetailDoelst.setVisible(false);
                oefOverview.setVisible(true);
                break;
        }
    }
    
    public void initNieuweOefening() {
        oefDetail.initNieuweOefening();
        oefOverview.clearSelectedItem();
        oefDetailGroepsb.nieuweOefening();
        oefDetailDoelst.nieuweOefening();
    }
}